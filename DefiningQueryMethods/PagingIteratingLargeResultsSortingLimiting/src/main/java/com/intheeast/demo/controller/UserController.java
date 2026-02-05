package com.intheeast.demo.controller;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO user = userService.findUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // lastname을 기준으로 첫 10명의 User를 페이지 형태로 조회
    @GetMapping("/lastname/queryFirst10")
    public ResponseEntity<Page<UserDTO>> queryFirst10ByLastname(@RequestParam String lastname, 
    		Pageable pageable) {
        Page<UserDTO> users = userService.queryFirst10ByLastname(lastname, pageable);
        return ResponseEntity.ok(users);
    }

    // lastname을 기준으로 상위 3명의 User를 Slice 형태로 조회
    @GetMapping("/lastname/findTop3")
    public ResponseEntity<Slice<UserDTO>> findTop3ByLastname(@RequestParam String lastname, 
    		Pageable pageable) {
        Slice<UserDTO> users = userService.findTop3ByLastname(lastname, pageable);
        return ResponseEntity.ok(users);
    }

    // lastname을 기준으로 첫 10명의 User를 Sort를 사용하여 리스트 형태로 조회
    @GetMapping("/lastname/findFirst10")
    public ResponseEntity<List<UserDTO>> findFirst10ByLastname(@RequestParam String lastname, 
    		Sort sort) {
        List<UserDTO> users = userService.findFirst10ByLastname(lastname, sort);
        return ResponseEntity.ok(users);
    }

    // lastname을 기준으로 상위 10명의 User를 페이지 형태로 조회
    @GetMapping("/lastname/findTop10")
    public ResponseEntity<List<UserDTO>> findTop10ByLastname(@RequestParam String lastname, 
    		Pageable pageable) {
        List<UserDTO> users = userService.findTop10ByLastname(lastname, pageable);
        return ResponseEntity.ok(users);
    }
    
    
    //  public List<UserDTO> findByLastNameThroughSortedAndLimited(
	//             String lastname, Sort sort, int limit)
    @GetMapping("/lastname/findLimit")
    public ResponseEntity<List<UserDTO>> findByLastNameThroughSortedAndLimited(
    		@RequestParam String lastname, Sort sort, int limit) {
    	List<UserDTO> users = userService.findByLastNameThroughSortedAndLimited(
    			lastname, sort, limit);
    	return ResponseEntity.ok(users);    	
    }
    
    
    
    
    
    
    
}