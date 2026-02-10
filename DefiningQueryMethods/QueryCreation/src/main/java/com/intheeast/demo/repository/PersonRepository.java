package com.intheeast.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intheeast.demo.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	
	List<Person> findByDepartmentName(String name);

}
