package com.intheeast.demo.controller;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 나이가 18세 이상인 사용자 목록 반환
    @GetMapping("/older-than-18")
    public ResponseEntity<List<UserDTO>> getUsersOlderThan18() {
        List<UserDTO> users = userService.findUsersOlderThan18();
        return ResponseEntity.ok(users);
    }
}