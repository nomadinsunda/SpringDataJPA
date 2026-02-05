package com.intheeast.demo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.intheeast.demo.entity.User;

import java.util.stream.Stream;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u")
    Stream<User> findAllByCustomQueryAndStream();

    Stream<User> readAllByFirstNameNotNull();

    @Query("select u from User u")
    Stream<User> streamAllPaged(Pageable pageable);
}
