package com.intheeast.demo.controller;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 특정 성을 가진 사용자 조회 및 정렬
    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<List<UserDTO>> getUsersByLastnameAndSort(
            @PathVariable String lastname,
            @RequestParam String sortBy) {
        List<UserDTO> users = userService.findByLastnameAndSort(lastname, sortBy);
        return ResponseEntity.ok(users);
    }

    // 성으로 조회하고 배열 형태로 반환
    @GetMapping("/lastname/array/{lastname}")
    public ResponseEntity<List<Object[]>> getUsersAsArrayAndSort(
            @PathVariable String lastname,
            @RequestParam String sortBy) {
        List<Object[]> users = userService.findByAsArrayAndSort(lastname, sortBy);
        return ResponseEntity.ok(users);
    }

    // 이름 끝으로 조회
    @GetMapping("/firstname/{firstname}")
    public ResponseEntity<List<UserDTO>> getUsersByFirstnameEndsWith(@PathVariable String firstname) {
        List<UserDTO> users = userService.findByFirstnameEndsWith(firstname);
        return ResponseEntity.ok(users);
    }

    // 사용자 추가
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }
    
    // 성 또는 이름으로 사용자 검색
    @GetMapping("/search")
    public ResponseEntity<UserDTO> findByLastnameOrFirstname(
            @RequestParam String lastname,
            @RequestParam String firstname) {
        UserDTO user = userService.findByLastnameOrFirstname(lastname, firstname);
        return ResponseEntity.ok(user);
    }
}