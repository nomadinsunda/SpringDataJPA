package com.intheeast.demo.service;

import org.springframework.stereotype.Service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long countByLastname(String lastname) {
        return userRepository.countByLastname(lastname);
    }

    @Transactional
    public long deleteByEmail(String email) { // 이메일로 삭제하는 메서드 추가
        return userRepository.deleteByEmail(email);
    }

    @Transactional
    public List<UserDTO> removeByEmail(String email) {
        List<User> users = userRepository.removeByEmail(email);
        return users.stream()
                    .map(UserDTO::fromEntity) // User -> UserDTO 변환
                    .collect(Collectors.toList());
    }

    public UserDTO saveUser(User user) {
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser); // User -> UserDTO 변환
    }

    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream()
                    .map(UserDTO::fromEntity) // User -> UserDTO 변환
                    .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return UserDTO.fromEntity(user); // User -> UserDTO 변환 (null 가능성 처리)
    }


    @Transactional
    public void updateUser(String firstname, String lastname, String email, String status) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setStatus(status);
        
    }
    
    // FirstName이 특정 prefix로 시작하는 유저 목록 조회
    public List<UserDTO> findByFirstNameStartingWith(String prefix) {
        List<User> users = userRepository.findByFirstnameStartingWith(prefix);
        return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
    }

    // LastName이 특정 문자열을 포함하는 유저 목록 조회
    public List<UserDTO> findByLastNameContaining(String substring) {
        List<User> users = userRepository.findByLastnameContaining(substring);
        return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
    }

    // Email에 특정 문자열이 없는 유저 목록 조회
    public List<UserDTO> findByEmailNotContaining(String substring) {
        List<User> users = userRepository.findByEmailNotContaining(substring);
        return users.stream()
                    .map(UserDTO::fromEntity)
                    .collect(Collectors.toList());
    }
}