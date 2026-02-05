package com.intheeast.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.intheeast.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
	// Pessimistic Lock을 사용한 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findById(Long id);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select u from User u")
    List<User> findAllUsers();
    
    //@Lock(LockModeType.PESSIMISTIC_WRITE)
    
}