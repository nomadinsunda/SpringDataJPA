package com.intheeast.demo.service;

import java.util.Map;

import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.support.WindowIterator;
import org.springframework.stereotype.Service;
import static com.intheeast.demo.util.JsonUtil.MAPPER;
import java.util.Base64;

import com.fasterxml.jackson.core.type.TypeReference;
import com.intheeast.demo.domain.Product;
import com.intheeast.demo.dto.ScrollResponse;
import com.intheeast.demo.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    /**
     * Offset 기반으로 특정 지점부터 데이터를 조회
     * @param offset 시작 지점 (0, 20, 40...)
     */
    public ScrollResponse getProductsByOffset(long offset) {
        // 1. 숫자로 된 offset을 ScrollPosition으로 변환
        ScrollPosition position = ScrollPosition.offset(offset);

        // 2. 데이터 조회 (기존 Repository 메서드 그대로 활용)
        // WindowIterator가 내부적으로 사용하는 것과 동일한 메커니즘으로 동작.
        Window<Product> window = productRepository.findFirst20ByOrderByPriceDescIdDesc(position);

        // 3. 다음 요청을 위한 offset 값 계산
        long nextOffset = offset + window.size();
        
        return new ScrollResponse(
            window.getContent(), 
            String.valueOf(nextOffset), // 다음 시작점 전달
            window.hasNext()
        );
    }

    public ScrollResponse getProducts(String cursor) {
        // 1. 커서가 없으면 처음부터, 있으면 해당 위치부터 시작하도록 설정
        KeysetScrollPosition position = (cursor == null || cursor.isEmpty()) 
            ? ScrollPosition.keyset() 
            : ScrollPosition.of(parseCursor(cursor), ScrollPosition.Direction.FORWARD);

        // 2. 데이터 조회
        Window<Product> window = productRepository.findFirst20ByOrderByPriceDescIdDesc(position);
        /*
         select
	        p1_0.id,
	        p1_0.name,
	        p1_0.price 
	     from
	        product p1_0 
	     order by
	        p1_0.price desc,
	        p1_0.id desc 
	     limit
	        ? --> binding parameter (1:INTEGER) <- [21]
         
         1. 첫 페이지 조회 쿼리 (Initial Request)
            현재 where 절이 없는 것으로 보아, 
            사용자가 커서(cursor) 없이 처음으로 목록을 요청했을 때의 쿼리.
            * order by p1_0.price desc, p1_0.id desc: 우리가 설정한 대로 가격 높은 순, 
              가격 같으면 ID 큰 순으로 정렬하고 있음.
            * limit ?: 한 번에 가져올 데이터 양을 제한.
         2. limit 21의 비밀 (왜 21인가?)
            로그 마지막 줄을 보면 binding parameter (1:INTEGER) <- [21]라고 되어 있음. 
            분명 우리는 페이지 사이즈를 20으로 예상했을 텐데, 왜 21개를 가져올까?
            * hasNext 판단 로직: Spring Data의 Slice나 Scroll 방식은 "다음 페이지가 있는지"를 확인하기 위해 
              사용자가 요청한 개수(size)보다 딱 1개 더 조회.
              * 21개를 조회했는데 진짜 21개가 다 나오면? -> "아, 다음 페이지가 더 있구나! (hasNext = true)"
              * 21개를 조회했는데 20개 이하로 나오면? -> "이게 마지막이구나! (hasNext = false)"
            * 즉, 프론트엔드에는 20개만 주고, 21번째 데이터는 존재 여부만 확인하는 용도로 쓰는 영리한 전략.
         */
        /*
           select
		        p1_0.id,
		        p1_0.name,
		        p1_0.price 
		    from
		        product p1_0 
		    where
		        p1_0.price<? 
		        or p1_0.price=? 
		        and p1_0.id<? 
		    order by
		        p1_0.price desc,
		        p1_0.id desc 
		    limit
		        ?
		    binding parameter (1:INTEGER) <- [9000]
		    binding parameter (2:INTEGER) <- [9000]
		    binding parameter (3:BIGINT) <- [81]
		    binding parameter (4:INTEGER) <- [21]
		    
		    두번째 페이지 조회 쿼리
		    1. 쿼리의 핵심: WHERE 절 분석
               사용자가 마지막으로 본 상품이 가격 9,000원 / ID 81번인 상황.
               * p1_0.price < 9000: 가격이 9,000원보다 낮은 상품들은 무조건 "다음" 데이터.
               * OR (p1_0.price = 9000 AND p1_0.id < 81): 가격이 똑같이 9,000원이라면, 
                 ID가 81보다 작은(더 예전에 등록된) 상품들을 가져오라는 뜻.
            2. 성능 포인트: 왜 효율적인가?
               만약 인덱스(price, id)가 없다면 DB는 1번 데이터부터 뒤져서 9,000원/81번을 찾아야 함. 
               하지만 인덱스가 있으면:
               * Index Seek: 인덱스 트리(B-Tree)에서 (9000, 81)이라는 
                 좌표를 단번에(O(log N)) 찾음.
               * Index Scan: 그 지점부터 내림차순(DESC) 방향으로 딱 21줄만 읽고 멈춤.
               * No Offset: "앞에 100만 건 건너뛰어!" 같은 무식한 과정이 없으므로 
                 데이터 양에 상관없이 응답 속도가 일정함.
             3. 주의 깊게 봐야 할 점: OR와 AND의 우선순위
                로그를 보면 where p1_0.price < ? or p1_0.price = ? and p1_0.id < ?라고 되어 있음. 
                SQL 연산자 우선순위상 AND가 OR보다 높기 때문에, 
                실제로는 우리가 의도한 대로 (price < 9000) OR (price = 9000 AND id < 81)로 해석됨. 
                Hibernate가 아주 정확한 표준 쿼리를 생성함.
         */
        // 3. 다음 조회를 위한 커서 추출 (데이터가 있으면 마지막 요소 기준 생성)
        String nextCursor = "";
        if (window.hasNext()) {
            // Window는 마지막 요소의 Keyset을 자동으로 추출하는 기능을 제공함
            nextCursor = encodeCursor(window.positionAt(window.size() - 1));
        }

        return new ScrollResponse(window.getContent(), nextCursor, window.hasNext());
    }

    // 간단한 예시용 인코딩/디코딩 로직 (실제로는 정교한 맵핑 필요)
    private Map<String, Object> parseCursor(String cursor) {
        try {
            return MAPPER.readValue(
                Base64.getUrlDecoder().decode(cursor), 
                new TypeReference<Map<String, Object>>() {}
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("유효하지 않은 커서 토큰입니다.", e);
        }
    }
    
    private String encodeCursor(ScrollPosition pos) {
        if (pos instanceof KeysetScrollPosition keysetPos) {
            try {
                // 1. Keyset의 Map 데이터를 JSON 바이트로 변환
            	byte[] jsonBytes = MAPPER.writeValueAsBytes(keysetPos.getKeys());
            	
                // 2. URL 안전한 Base64로 인코딩하여 반환
                return Base64.getUrlEncoder().encodeToString(jsonBytes);
            } catch (Exception e) {
                throw new RuntimeException("커서 인코딩 중 오류 발생", e);
            }
        }
        return null;
    }
}