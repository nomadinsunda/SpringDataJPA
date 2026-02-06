package com.intheeast.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intheeast.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByLastname(String lastname);

    long deleteByEmail(String email); // 이메일로 삭제 메서드 추가

    List<User> removeByEmail(String email); // 이메일로 삭제 후 목록 리턴 메서드 추가

	Optional<User> findByEmail(String email);
	
    // 파생된 쿼리 메서드
    List<User> findByFirstnameStartingWith(String prefix);

    List<User> findByLastnameContaining(String substring);

    List<User> findByEmailNotContaining(String substring);
    
    @Query("""
        SELECT u.status, COUNT(u)
        FROM User u
        WHERE u.status = :status
          AND u.email LIKE CONCAT('%', :domain)
          AND u.lastname IS NOT NULL
          AND u.lastname <> ''
        GROUP BY u.status
        ORDER BY COUNT(u) DESC
    """)
    List<Object[]> countUsersByStatusAndEmailDomain(
            @Param("status") String status,
            @Param("domain") String domain
    );
    

//    @Query("SELECT COUNT(u) FROM User u WHERE u.lastname = :lastname")
//    long countByLastname(@Param("lastname") String lastname);
//
//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    Optional<User> findByEmail(@Param("email") String email);
//
//    @Query("SELECT u FROM User u WHERE u.firstname LIKE CONCAT(:prefix, '%')")
//    List<User> findByFirstnameStartingWith(@Param("prefix") String prefix);
//
//    @Query("SELECT u FROM User u WHERE u.lastname LIKE CONCAT('%', :substring, '%')")
//    List<User> findByLastnameContaining(@Param("substring") String substring);
//
//    @Query("SELECT u FROM User u WHERE u.email NOT LIKE CONCAT('%', :substring, '%')")
//    List<User> findByEmailNotContaining(@Param("substring") String substring);

}