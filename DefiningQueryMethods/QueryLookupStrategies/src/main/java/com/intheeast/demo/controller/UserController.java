package com.intheeast.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	// @Component, @Service, @Repository 같은 어노테이션을 사용하여 
	// Spring 컨테이너에 등록된 Bean은 @Autowired를 사용하지 않고도 메서드 파라미터로 주입될 수 있지만, 
	// 필드에는 여전히 주입이 필요!
	@Autowired
    private UserService userService;

//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }

    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getFirstname(), 
        		userDTO.getLastname(),
        		userDTO.getEmail(),
        		userDTO.getStatus());
        return userService.saveUser(user);
        
    }

    // 모든 유저 조회
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    
    // 유저 조회 by ID : /users/by-id?id=1
    @GetMapping("/by-id")
    public UserDTO getUserById(@RequestParam Long id) {
        return userService.findById(id);
    }

    // 특정 성을 가진 유저 수 카운트
    @GetMapping("/count/{lastname}")
    public long countUsersByLastname(@PathVariable String lastname) {
        return userService.countByLastname(lastname);
    }

    // 특정 email을 가진 유저 삭제
    @DeleteMapping("/delete/{email}")
    public long deleteUsersByLastname(@PathVariable String email) {
        return userService.deleteByEmail(email);
    }

    // 특정 email을 가진 유저 삭제 후 목록 반환
    @DeleteMapping("/remove/{email}")
    public List<UserDTO> removeUsersByEmail(@PathVariable String email) {
        return userService.removeByEmail(email);
    }

    // 유저 업데이트
    @PutMapping("/{id}")
    public void updateUser(@RequestBody UserDTO userDTO) {
        userService.updateUser(userDTO.getFirstname(), 
        		userDTO.getLastname(),
        		userDTO.getEmail(),
        		userDTO.getStatus());
    }
    
    // FirstName이 특정 prefix로 시작하는 유저 조회
    @GetMapping("/search/firstname")
    public List<UserDTO> getUsersByFirstNameStartingWith(@RequestParam String prefix) {
        return userService.findByFirstNameStartingWith(prefix);
    }

    // LastName이 특정 문자열을 포함하는 유저 조회
    @GetMapping("/search/lastname")
    public List<UserDTO> getUsersByLastNameContaining(@RequestParam String substring) {
        return userService.findByLastNameContaining(substring);
    }

    // Email에 특정 문자열이 없는 유저 조회
    @GetMapping("/search/email-not-containing")
    public List<UserDTO> getUsersByEmailNotContaining(@RequestParam String substring) {
        return userService.findByEmailNotContaining(substring);
    }
}