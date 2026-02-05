package com.intheeast.demo.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@Entity
public class User extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;
    private Integer age;
    private Boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    // 생성자 (DTO를 생성할 때 유용)
    public User(String firstname, String lastname, Integer age, Boolean active) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.active = active;
    }

    // 역할 설정 및 추가 처리
    public void setUserRoles(Set<UserRole> userRoles) {
        if (userRoles != null) {
            // 기존 역할을 교체하여 중복 방지
            this.userRoles.clear();
            userRoles.forEach(this::addUserRole);  // 역할 추가 메서드로 위임
        }
    }

    public void addUserRole(UserRole userRole) {
        if (userRole != null) {
            userRoles.add(userRole);
            userRole.setUser(this);  // 양방향 참조 설정
        }
    }

    public void removeUserRole(UserRole userRole) {
        if (userRole != null) {
            userRoles.remove(userRole);
            userRole.setUser(null);  // 양방향 참조 제거
        }
    }
}