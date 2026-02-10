package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Page<User> queryByLastname(String lastname, Pageable pageable);
    
    // lastname을 기준으로 첫 10명의 User를 페이지 형태로 조회
	// First10은 Query Derivation 키워드
	/*
	 Spring Data JPA가 인식하는 키워드:
		First
        Top
        First10, Top5, Top100 등을 통해 생성되는 쿼리의
        limit 10은 Pageable과 무관하게 강제됩니다
	 */
    Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);
    /*
      select
        u1_0.id,
        u1_0.active,
        u1_0.age,
        u1_0.created_by,
        u1_0.creation_date,
        u1_0.firstname,
        u1_0.last_modified_by,
        u1_0.last_modified_date,
        u1_0.lastname 
    from
        user u1_0 
    where
        u1_0.lastname=? 
    order by
        u1_0.id desc 
    limit
        ?, ?   --> 의미는 첫번째 ?에 바인딩될 값은 offset 값, 두번째 ?에 바인딩될 값은 limit 값
 
    select
        count(u1_0.id) 
    from
        user u1_0 
    where
        u1_0.lastname=? 
    limit
        ?  --> limit 절이 붙은 이유는 count 집계 함수의 값은 1 row 이지만, 안전빵으로 하이버네이트가 limit 1을 설정

     */
    
    // lastname을 기준으로 User를 Slice 형태로 조회
    Slice<User> findByLastname(String lastname, Pageable pageable);
    
    // lastname을 기준으로 첫 10명의 User를 Sort를 사용하여 리스트 형태로 조회
    List<User> findFirst10ByLastname(String lastname, Sort sort);
    
    // lastname을 기준으로 상위 10명의 User를 페이지 형태로 조회
    List<User> findTop10ByLastname(String lastname, Pageable pageable);
    
    List<User> findByLastname(String lastname, Sort sort, Limit limit);
    
    // 대량의 데이터를 메모리에 다 올리지 않고 처리할 때
    Stream<User> readAllByLastname(String lastname, Sort sort);
}