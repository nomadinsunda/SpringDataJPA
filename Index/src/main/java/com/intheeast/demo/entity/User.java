package com.intheeast.demo.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;

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
@Table/*(name = "users", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_firstname_lastname", columnList = "first_name, last_name")
})*/
public class User extends BaseEntity{
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;
    
    private Integer age;
    private Boolean active;
    
    public User(String firstname, String lastname, String email, Integer age, Boolean active) {
    	this.firstName = firstname;
    	this.lastName = lastname;
    	this.email = email;
    	this.age = age;
    	this.active = active;
    }    
}