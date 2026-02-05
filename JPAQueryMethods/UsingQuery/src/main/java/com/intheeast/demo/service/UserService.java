package com.intheeast.demo.service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity)
                .orElse(null);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setFirstname(userDTO.getFirstname());
            user.setLastname(userDTO.getLastname());
            user.setAge(userDTO.getAge());
            user.setActive(userDTO.getActive());
            User updatedUser = userRepository.save(user);
            return UserDTO.fromEntity(updatedUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // 성으로 사용자 검색
    public List<UserDTO> getUsersByLastname(String lastname) {
        return userRepository.findByLastname(lastname)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 활성화된 사용자 검색
    public List<UserDTO> getActiveUsers() {
        return userRepository.findByActiveTrue()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 연령대에 따른 사용자 검색
    public List<UserDTO> getUsersByAgeRange(Integer minAge, Integer maxAge) {
        return userRepository.findUsersByAgeRange(minAge, maxAge)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 이름과 성으로 사용자 검색
    public UserDTO getUserByFirstnameAndLastname(String firstname, String lastname) {
        return userRepository.findByFirstnameAndLastname(firstname, lastname)
                .map(UserDTO::fromEntity)
                .orElse(null);
    }
    
    // 이름이 특정 문자열로 끝나는 사용자 조회
    public List<UserDTO> getUsersByFirstnameEndsWith(String firstname) {
        return userRepository.findByFirstnameEndsWith(firstname).stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}