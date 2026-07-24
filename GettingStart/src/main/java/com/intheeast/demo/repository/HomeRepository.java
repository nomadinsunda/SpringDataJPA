package com.intheeast.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.intheeast.demo.entity.Home;

public interface HomeRepository extends Repository<Home, Long> {
	
	Home save(Home home);

	// find...By : Subject
	// Id : Predicate
    Optional<Home> findById(long id);

}
