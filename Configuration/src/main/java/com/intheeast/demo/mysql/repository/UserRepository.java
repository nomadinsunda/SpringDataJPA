package com.intheeast.demo.mysql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.intheeast.demo.mysql.entity.User;

//@Repository : spring boot 3 이상은 Spring Data JPA의 Repository 인터페이스를 상속한 인터페이스에
//              더 이상 이 어노테이션을 적용하지 않아도 다음 인터페이스의 구현체가 자동으로 Spring IoC 컨테이너에 빈으로 등록됨
public interface UserRepository extends MyBaseRepository<User, Long> {

    long countByLastname(String lastname);

    long deleteByEmail(String email); // 이메일로 삭제 메서드 추가

    List<User> removeByEmail(String email); // 이메일로 삭제 후 목록 리턴 메서드 추가

	Optional<User> findByEmail(String email);

}