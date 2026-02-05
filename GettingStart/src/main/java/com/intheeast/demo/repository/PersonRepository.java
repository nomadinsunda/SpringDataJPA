package com.intheeast.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.*;


import com.intheeast.demo.entity.Person;


// Spring Boot 3 이상을 사용하고 있다면,
// @SpringBootApplication에 @EnableJpaRepositories가 마커 어노테이션으로 어딘가에 존재하므로
// Repository 인터페이스를 스캔해서 런타임에 구현체를 “동적으로 생성”하고 그 구현체를 Spring Bean으로 등록.
// 즉, 더 이상 @Repository를 사용할 필요가 없어짐.
//@Repository
//public interface PersonRepository extends CrudRepository<Person, Long> {
public interface PersonRepository extends Repository<Person, Long> {
	
	Person save(Person person);

    Optional<Person> findById(long id);
}