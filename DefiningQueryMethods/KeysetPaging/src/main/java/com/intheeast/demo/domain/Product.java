package com.intheeast.demo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
// price, id 순서로 이미 정렬되어 있는 인덱스 트리(B-Tree)를 타고 곧장 필요한 지점으로 이동
// : 인덱스를 만들었다는 건, DB안에 상품들이 이미 [가격 높은 순 -> 같은 가격이면 ID 큰 순]으로 
//   기차처럼 길게 줄을 서 있다는 뜻.
//    A상품: 70,000원 (ID 200)
//    B상품: 50,000원 (ID 180)
//    C상품: 50,000원 (ID 150) 
//    D상품: 50,000원 (ID 120) 
//    E상품: 45,000원 (ID 210) 
// 인덱스에 price뿐만 아니라 id까지 포함하는 이유는 중복된 값 처리 때문.
//   * 만약 가격(price)이 10,000원인 상품이 1,000개 있다면, 
//     price만으로는 "어디까지 읽었는지" 정확한 지점을 특정할 수 없음.
//   * 이때 고유값인 id를 인덱스에 추가하면, 가격이 같더라도 ID 순서로 명확한 순번이 정해짐. 
//     이를 Tie-breaking이라고 함.
// Keyset Scrolling은 보통 다음과 같은 쿼리를 실행. 
//  : SELECT * FROM product 
//      WHERE (price < 50000) OR (price = 50000 AND id < 150) ORDER BY price DESC, id DESC LIMIT 20;
//    A상품: 70,000원 (ID 200)
//    B상품: 50,000원 (ID 180)
//    C상품: 50,000원 (ID 150) ← [현재 사용자가 보고 있는 마지막 상품]
//    D상품: 50,000원 (ID 120) ← 다음 순서 1순위 (가격 같고 ID 작음)
//    E상품: 45,000원 (ID 210) ← 다음 순서 2순위 (가격 낮음)
// <<인덱스 설정 시 주의사항: 순서가 핵심!>>
//  columnList = "price, id"에서 컬럼의 순서가 매우 중요.
//   * JPA에서 OrderByPriceDescIdDesc를 사용한다면, 인덱스도 반드시 price가 먼저 오고 id가 다음에 와야 함.
//   * 만약 순서를 id, price로 바꾸면, price로 검색할 때 인덱스를 제대로 타지 못할 수도 있음
@Table(indexes = @Index(name = "idx_product_price_id", columnList = "price, id"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    @NotNull
    private int price;
    
    // Getter, Setter, Constructors...
}