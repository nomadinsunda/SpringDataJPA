package com.intheeast.demo.controller;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // [Paging - Page 반환]
    // PageableHandlerMethodArgumentResolver는 Pageable 인터페이스의 구현체로 PageRequest 전달.
    //   * 자동 파싱: 브라우저에서 /api/users/lastname/queryFirst10?lastname=Brown&page=0&size=10&sort=id,desc라고 요청을 보내면, 
    //           리졸버가 이 값을 읽어 PageRequest 객체 안에 페이지 번호, 한 페이지당 개수, 정렬 조건을 담아 컨트롤러에 전달.
    //   * 디폴트 값 적용: 만약 쿼리 파라미터가 없다면, 스프링이 설정한 디폴트값(보통 page=0, size=20)을 사용하여 객체를 만듭니다.
    //                 커스텀하고 싶다면 @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable 처럼 어노테이션을 붙일 수 있습니다.
    // lastname을 기준으로 첫 10명의 User를 페이지 형태로 조회
    // Page 구현체는 PageImpl임.
    @GetMapping("/lastname/queryFirst10")
    public ResponseEntity<Page<UserDTO>> queryFirst10ByLastname(@RequestParam String lastname, 
    		Pageable pageable) {
    	PageImpl pil;
        Page<UserDTO> users = userService.queryFirst10ByLastname(lastname, pageable);
//    	Page<UserDTO> users = userService.queryByLastname(lastname, pageable);
        return ResponseEntity.ok(users);
    }
    /*
         {
		    "content": [
		        {
		            "firstname": "Emily",
		            "lastname": "Brown",
		            "age": 28,
		            "active": true
		        },
		        {
		            "firstname": "Michael",
		            "lastname": "Brown",
		            "age": 26,
		            "active": false
		        },
		        {
		            "firstname": "Laura",
		            "lastname": "Brown",
		            "age": 30,
		            "active": false
		        },
		        {
		            "firstname": "James",
		            "lastname": "Brown",
		            "age": 40,
		            "active": true
		        },
		        {
		            "firstname": "David",
		            "lastname": "Brown",
		            "age": 29,
		            "active": false
		        },
		        {
		            "firstname": "Jessica",
		            "lastname": "Brown",
		            "age": 32,
		            "active": false
		        },
		        {
		            "firstname": "John",
		            "lastname": "Brown",
		            "age": 22,
		            "active": true
		        },
		        {
		            "firstname": "Laura",
		            "lastname": "Brown",
		            "age": 35,
		            "active": true
		        },
		        {
		            "firstname": "Michael",
		            "lastname": "Brown",
		            "age": 40,
		            "active": false
		        },
		        {
		            "firstname": "Alex",
		            "lastname": "Brown",
		            "age": 38,
		            "active": true
		        }
		    ],
		    "pageable": {
		        "pageNumber": 0, // 요청받은 페이지 번호
		        "pageSize": 10,  // 요청받은 페이지 크기
		        "sort": {
		            "empty": false,
		            "sorted": true, // 정렬 조건이 적용된 상태
		            "unsorted": false
		        },
		        "offset": 0, // DB에서 데이터를 가져올 때 앞에서 몇 개를 건너뛰었는지를 나타냄. 만약 2페이지(page=1)라면 offset은 10.
		        "paged": true, // 현재 페이징 처리가 적용된 쿼리임을 나타냅니다. (unpaged는 그 반대)
		        "unpaged": false
		    },
		    "last": false, // 현재 위치가 처음인지 마지막인지를 알려주는 불리언 값
		    "totalPages": 5, // 전체 데이터를 페이지당 개수(size)로 나눈 총 페이지 수. (49\10을 올림한 값)
		    "totalElements": 49, // 조건(lastname이 Brown인)에 맞는 DB 전체 데이터 수
		    "first": true, // 현재 위치가 처음인지 알려주는 불리언 값
		    "numberOfElements": 10, // 현재 이 응답(content)에 담긴 실제 데이터 개수. 보통 size와 같지만, 마지막 페이지에서는 더 적을 수 있음.
		    "size": 10, // 한 페이지에 담기로 설정된 최대 데이터 개수
		    "number": 0, // 현재 페이지 번호입니다. 0부터 시작하므로 실제로는 '1페이지'를 의미
		    "sort": {
		        "empty": false,
		        "sorted": true,
		        "unsorted": false
		    },
		    "empty": false // 결과 데이터(content)가 비어있는지 여부
		}
		
	 다음 페이지 조회를 위해 frontend는 다음과 같은 url을 요청하면 됨.
	 : /api/users/lastname/queryFirst10?lastname=Brown&page=1&size=10&sort=id,desc
     */

    // [Iterating Large Result - Slice 리턴 (무한 스크롤)]
    // lastname을 기준으로 상위 3명의 User를 Slice 형태로 조회
    // /api/users/lastname/slice?lastname=Brown&page=0&size=3
    @GetMapping("/lastname/slice")
    public ResponseEntity<Slice<UserDTO>> findByLastnameSlice(
            @RequestParam String lastname, 
            Pageable pageable) {
        Slice<UserDTO> users = userService.findUsersByLastnameSlice(lastname, pageable);
        return ResponseEntity.ok(users);
    }
    /*
     항목	            Page (이전 결과)	        Slice (현재 결과)
	 totalElements	있음 (전체 49개)	        없음
     totalPages	    있음 (전체 5페이지)	        없음
     count 쿼리	    실행함 (SELECT COUNT...)	실행 안 함 (성능 이득)
     핵심 필드	        last (마지막 여부)	        last 및 내부적 hasNext
     
     Slice의 철학은 
     "전체 개수가 몇 개인지는 몰라도 되니, 지금 당장 다음 페이지가 있는지만 알려줘"임.
     성능 최적화: 데이터가 수백만 건일 때 COUNT(*) 쿼리는 매우 무거움. 
     Slice는 이 과정을 생략하므로 대규모 서비스에서 훨씬 빠름.
     무한 스크롤 적합: 사용자가 인스타그램 피드를 내릴 때 전체 게시물이 몇 개인지는 중요하지 않음? 오직 "더 불러올 데이터가 있는가"만 중요.
     */

    // [Sorting - List 반환 (정적 제한 + 동적 정렬)]
    // lastname을 기준으로 첫 10명의 User를 Sort를 사용하여 리스트 형태로 조회
    // 가장 기본적인 호출 (ID 오름차순 기본 정렬) http://localhost:8080/api/users/lastname/findFirst10?lastname=Brown
    // 나이 내림차순으로 정렬하여 상위 10명 호출 http://localhost:8080/api/users/lastname/findFirst10?lastname=Brown&sort=age,desc
    // 여러 조건으로 정렬 (나이 내림차순, 이름 오름차순) http://localhost:8080/api/users/lastname/findFirst10?lastname=Brown&sort=age,desc&sort=firstname,asc
    @GetMapping("/lastname/findFirst10")
    public ResponseEntity<List<UserDTO>> findFirst10ByLastname(@RequestParam String lastname, 
    		Sort sort) {
        List<UserDTO> users = userService.findFirst10ByLastname(lastname, sort);
        return ResponseEntity.ok(users);
    }

    // [Limiting - List 리턴 (Pageable 활용 개수 제한)]
    // 메서드 이름에 의한 정적 제한(Top10)과 Pageable에 의한 동적 제한이 공존
    // 기본 테스트 (상위 10명) http://localhost:8080/api/users/lastname/findTop10?lastname=Brown
    // 사이즈를 더 작게 조절 테스트 (상위 5명) http://localhost:8080/api/users/lastname/findTop10?lastname=Brown&size=5
    // 정렬 조건 추가 테스트 (나이가 많은 상위 10명) http://localhost:8080/api/users/lastname/findTop10?lastname=Brown&size=10&sort=age,desc
    // "메서드 이름은 Top10인데, Postman에서 size=5 혹은 size=20을 보내면 어떻게 될까?"
    //  * size=5를 보낼 때: 5개만 나옴. Pageable의 동적 제한이 더 구체적인 요청으로 간주되기 때문.
    //  * size=20을 보낼 때: 10개만 나옴. 메서드 이름의 Top10이 전체 ResultSet의 최대 한도(Hard Limit) 역할을 하기 때문.
    /*
      왜 결과 타입이 Page가 아닌 List인가?
      findTop10...은 이미 결과의 상한선이 정해져 있음.
      Page 객체는 "전체 개수"를 기반으로 페이지네이션을 하는 용도인데, 
      Top10은 말 그대로 "딱 10개만 관심 있다"는 뜻이므로 
      굳이 무거운 Count 쿼리를 날릴 필요가 없음.
      따라서 보통 TopN 쿼리는 List나 Slice로 반환받는 것이 성능상 유리.
     */
    @GetMapping("/lastname/findTop10")
    public ResponseEntity<List<UserDTO>> findTop10ByLastname(@RequestParam String lastname, 
    		Pageable pageable) {
        List<UserDTO> users = userService.findTop10ByLastname(lastname, pageable);
        return ResponseEntity.ok(users);
    }
    
    // [Limiting - Limit 인터페이스 활용]
    // "Brown 성씨를 가진 사람 중 2명만 가져와" http://localhost:8080/api/users/lastname/findLimit?lastname=Brown&limit=2
    // "나이가 적은 순으로 7명만 가져와" http://localhost:8080/api/users/lastname/findLimit?lastname=Brown&limit=7&sort=age,asc
    /*
     TopN vs Limit 인터페이스
      1.유연성 (Flexibility):
         * findTop10: 무조건 최대 10개로 고정됩니다. 15개를 가져오고 싶으면 메서드를 새로 만들어야 함.
         * findLimit: 호출하는 쪽(Postman/클라이언트)에서 limit=15를 보내면 15개를 가져옴. 메서드 하나로 모든 개수 제한에 대응 가능.
      2.가독성: 메서드 이름이 findByLastname으로 깔끔해지며, 개수 제한 로직이 파라미터로 분리되어 코드가 더 직관적.
      3.Pageable과의 차이: Pageable은 페이징 정보를 모두 포함(page, size, sort)하지만, Limit은 오직 "최대 몇 개"라는 정보만 전달할 때 사용하는 더 가벼운 도구.
     */
    @GetMapping("/lastname/findLimit")
    public ResponseEntity<List<UserDTO>> findByLastNameThroughSortedAndLimited(
    		@RequestParam String lastname, Sort sort, int limit) {
    	List<UserDTO> users = userService.findByLastNameThroughSortedAndLimited(
    			lastname, sort, limit);
    	return ResponseEntity.ok(users);    	
    }
    
    /*
     실무에서,,,
      * 게시판처럼 전체 페이지 번호가 필요할 때 → Page
      * 인스타그램처럼 무한 스크롤을 구현할 때 → Slice
      * 딱 정해진 개수(예: 최근 공지사항 5개)만 가져올 때 → findTop5 (정적) 또는 Limit (동적)
    */
    
    /**
     * [Iterating Large Results - Stream 활용]
     * URL: /api/users/lastname/process-stream?lastname=Brown&sort=id,desc
     * * 특징:
     * 1. List로 한꺼번에 담지 않고 DB 커서를 통해 한 명씩 읽어 처리.
     * 2. 서비스 레이어에서 @Transactional(readOnly = true) 처리가 필수.
     * 3. 메모리 부하를 최소화하며 대량 데이터를 순회(Iterating)할 수 있음.
     * 
     1. 대량의 데이터 배치(Batch) 처리
        예를 들어, 성이 'Brown'인 모든 사용자(약 10만 명)에게 "서비스 업데이트 안내 이메일"을 보내야 한다고 가정.
        * List 방식: 10만 명을 한꺼번에 메모리에 다 불러온 뒤 이메일을 보냅니다. (메모리 폭발 위험)
        * Stream 방식: DB에서 한 명씩 꺼내서 이메일을 보내고 바로 메모리에서 치웁니다. (메모리 안정성)
     2. 대용량 파일 생성 (엑셀, CSV 다운로드)
        관리자 페이지에서 "전체 회원 목록 다운로드" 버튼을 눌렀을 때를 생각해 보세요.
        * 회원이 100만 명이면 List 구현체에 담는 것 자체가 불가능합니다.
        * 이때 Stream을 열어서 DB에서 한 줄 읽고, 파일에 한 줄 쓰고, 다시 한 줄 읽고 쓰는 방식을 사용합니다. 
        * 서버는 메모리를 거의 쓰지 않고도 몇 기가바이트(GB) 짜리 파일을 만들어낼 수 있습니다.
     3. 데이터 마이그레이션 및 변환
        A 테이블에 있는 데이터를 읽어서 가공한 뒤 B 테이블로 옮기거나, 외부 분석 시스템으로 전송해야 할 때 사용합니다.
        * userStream.map(UserDTO::fromEntity).forEach(externalApi::send)
        * 이런 식으로 한 명씩 변환해서 외부로 쏴주는 "파이프라인" 역할을 합니다.
     */
    @GetMapping("/lastname/process-stream")
    public ResponseEntity<String> processUserStream(@RequestParam String lastname, Sort sort) {
        userService.processAllUsersByLastname(lastname, sort);
        return ResponseEntity.ok("Stream processing completed for lastname: " + lastname);
    }
    
    
    
}