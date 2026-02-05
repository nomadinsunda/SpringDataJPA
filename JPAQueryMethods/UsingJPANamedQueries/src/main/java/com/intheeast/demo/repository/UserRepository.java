package com.intheeast.demo.repository;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    
    List<User> findByLastname(String lastname);
    
    @Query(name = "User.findByActive")
    List<User> findUsersByActive(Boolean active);
    
    @Query(name = "User.findByAgeGreaterThan")
    List<User> findUsersByAgeGreaterThan(Integer age);
}
