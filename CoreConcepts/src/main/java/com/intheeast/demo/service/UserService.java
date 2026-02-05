package com.intheeast.demo.service;

import org.springframework.stereotype.Service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
    	// 현재 email 속성은 unique 제약조건이 설정되었기 때문에,
    	// 동일한 email 주소를 가지는 복수개의 row는 존재할 수 없다.
    	// 하지만, 가정[강의를 위해서]을 한다는 것.
        List<User> users = userRepository.removeByEmail(email);   
        /*
          public static UserDTO fromEntity(User user) {
	        if (user == null) {
	            return null;
	        }
	        return new UserDTO(user.getFirstname(), user.getLastname(), user.getEmail());
    	 }
         */
        return users.stream() // 선언형 프로그래밍!!! : 직관적임
                    .map(UserDTO::fromEntity) // User -> UserDTO 변환
                    .collect(Collectors.toList());
    }

    @Transactional
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
    	// Optional<User> findById(ID id);
        User user = userRepository.findById(id).orElse(null);
        return UserDTO.fromEntity(user); // User -> UserDTO 변환 (null 가능성 처리)
    }


    @Transactional
    public void updateUser(String firstname, String lastname, String email) {
    	//public <RuntimeException> T orElseThrow(Supplier<RuntimeException> exceptionSupplier) throws RuntimeException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstname(firstname);
        user.setLastname(lastname);        
    }
}