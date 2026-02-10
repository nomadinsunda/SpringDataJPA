package com.intheeast.demo.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.*;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
//import jakarta.persistence.NamedQuery;
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
//@Table(name = "users", indexes = {
//		 @Index(name = "idx_users_age", columnList = "age")
//})
public class User extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private Integer age;
    private Boolean active;
    
    public User(String firstname, String lastname, Integer age, Boolean active) {
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.age = age;
    	this.active = active;
    }

    
}