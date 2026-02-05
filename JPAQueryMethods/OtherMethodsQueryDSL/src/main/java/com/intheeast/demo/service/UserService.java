package com.intheeast.demo.service;

import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepositoryCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepositoryCustom userRepositoryCustom;

    @Autowired
    public UserService(@Qualifier("userRepositoryImpl") UserRepositoryCustom userRepositoryCustom) {
        this.userRepositoryCustom = userRepositoryCustom;
    }

    // 나이가 18세 이상인 사용자 조회
    public List<UserDTO> findUsersOlderThan18() {
        List<User> users = userRepositoryCustom.customQueryMethod();
        return users.stream()
                .map(UserDTO::fromEntity)
                .collect(Collectors.toList());
    }
}