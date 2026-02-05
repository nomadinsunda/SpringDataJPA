package com.intheeast.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    // N:1 관계 설정 - 여러 UserRole이 하나의 User에 속함
    @ManyToOne
    @JoinColumn(name = "user_id") // 외래 키 설정
    private User user;

    public UserRole(String role) {
        this.role = role;
    }
}