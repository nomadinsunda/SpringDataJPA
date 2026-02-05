package com.intheeast.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.intheeast.demo.entity.User;

public interface UserRepository extends MyBaseRepository<User, Long> {

    long countByLastname(String lastname);

    long deleteByEmail(String email); // 이메일로 삭제 메서드 추가

    List<User> removeByEmail(String email); // 이메일로 삭제 후 목록 리턴 메서드 추가

	Optional<User> findByEmail(String email);
	
	@Query("SELECT u FROM User u WHERE u.hello > :age") 
	// @Param : Annotation to bind method parameters to a query via a named parameter.
	// named parameter : "age" --> :age
	List<User> findUsersOlderThan(@Param("age") int age);

}