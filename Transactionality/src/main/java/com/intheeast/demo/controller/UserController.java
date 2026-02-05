package com.intheeast.demo.controller;

import com.intheeast.demo.service.UserManagement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserManagement userManagement;

    public UserController(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    @PostMapping("/role/add")
    public ResponseEntity<Void> addRoleToAllUsers(@RequestParam String roleName) {
        userManagement.addRoleToAllUsers(roleName);
        return ResponseEntity.ok().build();
    }

}