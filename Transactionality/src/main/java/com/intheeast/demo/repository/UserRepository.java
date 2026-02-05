package com.intheeast.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intheeast.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // 추가적인 쿼리 메서드 정의 가능
}