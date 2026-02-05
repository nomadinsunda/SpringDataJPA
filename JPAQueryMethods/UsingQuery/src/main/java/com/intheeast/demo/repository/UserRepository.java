package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
	// 성(lastname)으로 사용자 검색
    List<User> findByLastname(String lastname);

    // 활성화된 사용자 검색
    List<User> findByActiveTrue();
    
    // 연령대에 따라 사용자 검색
    @Query("SELECT u FROM User u WHERE u.age >= :minAge AND u.age <= :maxAge")
    List<User> findUsersByAgeRange(@Param("minAge") Integer minAge, @Param("maxAge") Integer maxAge);

    // 이름과 성으로 사용자 검색
    @Query("SELECT u FROM User u WHERE u.firstname = :firstname AND u.lastname = :lastname")
    Optional<User> findByFirstnameAndLastname(@Param("firstname") String firstname, @Param("lastname") String lastname);
    
    // 이름 끝이 특정 문자열로 끝나는 사용자 조회
    @Query("select u from User u where u.firstname like %?1")
    List<User> findByFirstnameEndsWith(String firstname);
}