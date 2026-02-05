package com.intheeast.demo.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

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
public class User extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstname;
    private String lastname;
    private Integer age;
    private Boolean active;
    
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();
    
    public User(String firstname, String lastname, Integer age, Boolean active) {
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.age = age;
    	this.active = active;
    }
    
 // Set user roles
    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles.clear(); // Clear existing roles if any
        if (userRoles != null) {
            this.userRoles.addAll(userRoles);
            for (UserRole userRole : userRoles) {
                userRole.setUser(this); // Set back-reference
            }
        }
    }
    
    // Add a single user role
    public void addUserRole(UserRole userRole) {
        userRoles.add(userRole);
        userRole.setUser(this); // Set back-reference
    }

    // Remove a user role
    public void removeUserRole(UserRole userRole) {
        userRoles.remove(userRole);
        userRole.setUser(null); // Clear back-reference
    }

}