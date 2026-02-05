package com.intheeast.demo.controller;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.service.UserService;
import com.intheeast.demo.repository.UserRepository.NamesOnly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 사용자의 이름으로 검색
    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.findUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    // 사용자의 성으로 검색
    
    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<Collection<NamesOnly>> getUsersByLastname(@PathVariable String lastname) {
        Collection<NamesOnly> namesOnly = userService.findUsersByLastName(lastname);
        return new ResponseEntity<>(namesOnly, HttpStatus.OK);
    }
    
//    @GetMapping("/last-name/{lastName}")
//    public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
//        List<User> users = userService.findUsersByLastName(lastName);
//        return ResponseEntity.ok(users);
//    }

    // 이메일로 사용자 검색
    @GetMapping("/email/{email}")
    public ResponseEntity<List<User>> getUserByEmail(@PathVariable String email) {
        List<User> users = userService.findUserByEmail(email);
        return ResponseEntity.ok(users);
    }

    // 사용자 저장
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
