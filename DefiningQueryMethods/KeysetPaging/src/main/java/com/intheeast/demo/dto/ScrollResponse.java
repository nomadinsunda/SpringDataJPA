package com.intheeast.demo.dto;

import java.util.List;

import com.intheeast.demo.domain.Product;

// record
//  : 데이터 전달만을 목적으로 하는 순수 데이터 객체(DTO)를 아주 간결하게 만들기 위한 특수 클래스
public record ScrollResponse(
 List<Product> content,
 String nextCursor,
 boolean hasNext
) {}
// 위 한 줄만 쓰면 자바 컴파일러가 다음을 자동으로 다 만들어줌.
// * private final 필드 (name, price)
// * 모든 필드를 초기화하는 생성자
// * 필드와 이름이 같은 Getter 메서드 (주의: getName()이 아니라 그냥 name()입니다!)
// * equals(), hashCode(), toString()
