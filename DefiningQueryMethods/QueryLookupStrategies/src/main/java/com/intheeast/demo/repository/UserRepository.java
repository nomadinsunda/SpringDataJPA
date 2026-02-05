package com.intheeast.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

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

}