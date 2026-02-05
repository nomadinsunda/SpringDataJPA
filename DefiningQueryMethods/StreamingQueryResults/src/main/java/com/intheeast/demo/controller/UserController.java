package com.intheeast.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 모든 사용자 조회
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers();
    }

    // 사용자 생성
    /*
     * Postman에서 다음과 같이 보내어서 mapping이 안됨.
     {
    	"firstName": "NewFirstName",
    	"lastName": "NewLastName"
	 }
	 UserDTO의 필드 이름과 동일한 이름을 전송해야 함.
	 다음과 같이 수정해서 보내야 함
	 {
    	"firstname": "NewFirstName",
    	"lastname": "NewLastName"
	 }
     */
    @PostMapping
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    // 특정 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.findUserById(id);
        return userDTO != null ? ResponseEntity.ok(userDTO) : ResponseEntity.notFound().build();
    }

    // 사용자 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUserDTO = userService.updateUser(id, userDTO);
        return updatedUserDTO != null ? ResponseEntity.ok(updatedUserDTO) : ResponseEntity.notFound().build();
    }

    // 사용자 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
