package com.intheeast.demo.service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
//    public UserService(UserRepository userRepository) {
//    	this.userRepository = userRepository;
//    }

    // 사용자 저장 (락을 걸고 실행)
    @Transactional
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userDTO.toEntity();
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }

    // 락을 이용한 ID로 사용자 찾기
    @Transactional
    public UserDTO findUserByIdWithLock(Long id) {
        Optional<User> user = userRepository.findById(id);  // Pessimistic Lock 적용됨
        return user.map(UserDTO::fromEntity).orElse(null);
    }

    // 모든 사용자 조회
    @Transactional
    public List<UserDTO> findAllUsers() {   	
    	
        return userRepository.findAllUsers().stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
 
    // 사용자 삭제
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}