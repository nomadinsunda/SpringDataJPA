package com.intheeast.demo.service.impl;

import com.intheeast.demo.entity.Role;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.entity.UserRole;
import com.intheeast.demo.repository.RoleRepository;
import com.intheeast.demo.repository.UserRepository;
import com.intheeast.demo.service.UserManagement;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementImpl implements UserManagement {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserManagementImpl(UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void addRoleToAllUsers(String roleName) {
        // 주어진 역할 이름으로 역할을 찾습니다.
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            throw new IllegalArgumentException("Role not found: " + roleName);
        }
        
        // 모든 사용자를 가져옵니다.
        List<User> users = userRepository.findAll();

        // 모든 사용자에게 역할을 추가합니다.
        for (User user : users) {
            // UserRole 객체를 생성하고 사용자에게 추가합니다.
            UserRole userRole = new UserRole();
            userRole.setRole(role); // 역할 설정
            user.addUserRole(userRole); // 사용자에게 역할 추가
        }
        
        // 모든 사용자를 한 번에 저장합니다.
        try {
            userRepository.saveAll(users);
        } catch (DataAccessException e) {
            // 예외 발생 시 롤백
            throw new RuntimeException("Failed to save users", e);
        }
    }

}