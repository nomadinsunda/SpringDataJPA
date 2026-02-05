package com.intheeast.demo.h2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.intheeast.demo.h2.entity.Person;


public interface PersonRepository extends Repository<Person, Long> {
	
	Person save(Person person);

	Optional<Person> findById(long id);
	
	long countByLastname(String lastname);
	
	List<Person> findAll();
	
	List<Person> findByLastname(String lastName);
}