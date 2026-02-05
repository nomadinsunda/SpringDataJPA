package com.intheeast.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intheeast.demo.entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    // 필요에 따라 커스텀 쿼리 메서드를 추가할 수 있습니다.
    
    // 예시: 특정 사용자 ID로 UserRole 조회
    List<UserRole> findByUserId(Long userId);
    
    // 예시: 특정 역할 ID로 UserRole 조회
    List<UserRole> findByRoleId(Long roleId);
}