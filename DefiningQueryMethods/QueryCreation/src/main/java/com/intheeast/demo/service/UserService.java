package com.intheeast.demo.service;

import org.springframework.stereotype.Service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

import java.time.LocalDateTime;
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
    
 // 이메일과 성으로 사용자 검색
    public List<UserDTO> findByEmailAndLastname(String emailAddress, String lastname) {
        return userRepository.findByEmailAndLastname(emailAddress, lastname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 성 또는 이름으로 중복 제거된 사용자 검색
    public List<UserDTO> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname) {
        return userRepository.findDistinctByLastnameOrFirstname(lastname, firstname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 성을 기준으로 대소문자를 무시하고 사용자 검색
    public List<UserDTO> findByLastnameIgnoreCase(String lastname) {
        return userRepository.findByLastnameIgnoreCase(lastname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 성과 이름을 기준으로 대소문자를 무시하고 사용자 검색
    public List<UserDTO> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname) {
        return userRepository.findByLastnameAndFirstnameAllIgnoreCase(lastname, firstname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 성을 기준으로 이름을 오름차순으로 정렬하여 사용자 검색
    public List<UserDTO> findByLastnameOrderByFirstnameAsc(String lastname) {
        return userRepository.findByLastnameOrderByFirstnameAsc(lastname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 성을 기준으로 이름을 내림차순으로 정렬하여 사용자 검색
    public List<UserDTO> findByLastnameOrderByFirstnameDesc(String lastname) {
        return userRepository.findByLastnameOrderByFirstnameDesc(lastname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
 // 특정 범위 내의 사용자 검색
    public List<UserDTO> findUsersByCreationDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<User> users = userRepository.findByCreationDateBetween(startDate, endDate);
        return users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    // 특정 날짜 이전의 사용자 검색
    public List<UserDTO> findUsersByCreationDateLessThan(LocalDateTime date) {
        List<User> users = userRepository.findByCreationDateLessThan(date);
        return users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }

    // 이메일에 특정 문자열이 포함된 사용자 검색
    public List<UserDTO> findUsersByEmailContaining(String keyword) {
        List<User> users = userRepository.findByEmailContaining(keyword);
        return users.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }
}