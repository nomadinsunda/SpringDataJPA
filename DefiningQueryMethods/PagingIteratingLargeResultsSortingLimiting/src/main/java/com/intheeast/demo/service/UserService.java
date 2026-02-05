package com.intheeast.demo.service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
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

    public List<UserDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public UserDTO saveUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    public UserDTO findUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDTO::fromEntity)
                .orElse(null); // 적절한 예외 처리 필요
    }

    // lastname을 기준으로 첫 10명의 User를 페이지 형태로 조회
    public Page<UserDTO> queryFirst10ByLastname(String lastname, Pageable pageable) {
        return userRepository.queryFirst10ByLastname(lastname, pageable)
                .map(UserDTO::fromEntity);
    }

    // lastname을 기준으로 상위 3명의 User를 Slice 형태로 조회
    public Slice<UserDTO> findTop3ByLastname(String lastname, Pageable pageable) {
        return userRepository.findTop3ByLastname(lastname, pageable)
                .map(UserDTO::fromEntity);
    }

    // lastname을 기준으로 첫 10명의 User를 Sort를 사용하여 리스트 형태로 조회
    public List<UserDTO> findFirst10ByLastname(String lastname, Sort sort) {
        return userRepository.findFirst10ByLastname(lastname, sort)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // lastname을 기준으로 상위 10명의 User를 페이지 형태로 조회
    public List<UserDTO> findTop10ByLastname(String lastname, Pageable pageable) {
        return userRepository.findTop10ByLastname(lastname, pageable)
                .stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    // List<User> findByLastname(String lastname, Sort sort, Limit limit);
    public List<UserDTO> findByLastNameThroughSortedAndLimited(
    		String lastname, Sort sort, int limit) {
    	return userRepository.findByLastname(lastname, sort, Limit.of(limit))
    			.stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    	
    }
    
    
    
    
    
    
}