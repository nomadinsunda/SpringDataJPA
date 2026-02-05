package com.intheeast.demo.service;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.exception.ConflictException;
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
    	if (user.isNew()) {
            System.out.println("New User(O): Persisting the user.");
        } else {
            System.out.println("Existing User: Updating the user.");
        }
    	////////////////////////////////////////////
        User savedUser = userRepository.save(user);
        ///////////////////////////////////////////
        
        //
        if (!savedUser.isNew()) {
            System.out.println("New User(X): Persisting the user.");
        } else {
            System.out.println("Existing User: Updating the user.");
        }
        
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
        if (user != null && !user.isNew()) {
            System.out.println("User is already persisted.");
        }
        return UserDTO.fromEntity(user); 
    }


    // 낙관적 잠금은 트랜잭션이 완료되기 전에 데이터의 충돌 여부를 확인할 수 있어, 실패 시 트랜잭션을 롤백할 수 있습니다.
    // 낙관적 락은 항상 작동 중임...그 이유는 다음 세가지 상태 조건이 만족되었기 때문임!!!
    // @Version O
    // @Transactional
    // 엔티티 수정
    @Transactional
    public void updateUser(String firstname, String lastname, String email) {
    	// @Version 어노테이션을 적용함으로써 다음 예외 처리를 적절한 리팩토링임!!!
    	try {
	        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
	        user.setFirstname(firstname);
	        user.setLastname(lastname);  // Dirty Checking이 발생할 것...
	        
    	} catch (ObjectOptimisticLockingFailureException e) {
            throw new ConflictException("이미 다른 사용자가 수정했습니다.");
    	}
        
    }
}