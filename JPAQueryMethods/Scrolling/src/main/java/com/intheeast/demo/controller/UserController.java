package com.intheeast.demo.controller;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/pagesize")
    public ResponseEntity<List<UserDTO>> getUsersWithOffset(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<UserDTO> users = userService.getUsersWithOffset(page, size);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/age")
    public ResponseEntity<List<UserDTO>> getUsersByAge(@RequestParam Integer age) {
        List<UserDTO> users = userService.getBooksUsingOffset(age);
        return ResponseEntity.ok(users);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers(@RequestParam int ageThreshold) {
        List<UserDTO> users = userService.getUsersWithWindowIterator(ageThreshold);
        return ResponseEntity.ok(users);
    }
}
