package com.intheeast.demo.service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findByLastnameAndSort(String lastname, String sortBy) {
        List<User> users = userRepository.findByAndSort(lastname, Sort.by(sortBy));
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<Object[]> findByAsArrayAndSort(String lastname, String sortBy) {
        return userRepository.findByAsArrayAndSort(lastname, Sort.by(sortBy));
    }

    public List<UserDTO> findByFirstnameEndsWith(String firstname) {
        List<User> users = userRepository.findByFirstnameEndsWith(firstname);
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // 사용자 추가 메서드 (DTO를 사용)
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }
    
    // 성 또는 이름으로 사용자 검색
    public UserDTO findByLastnameOrFirstname(String lastname, String firstname) {
        User user = userRepository.findByLastnameOrFirstname(lastname, firstname);
        return UserDTO.fromEntity(user);
    }
}