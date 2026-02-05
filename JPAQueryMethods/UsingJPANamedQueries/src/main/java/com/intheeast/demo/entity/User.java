package com.intheeast.demo.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
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
@NamedQuery(name = "User.findByLastname", 
            query = "SELECT u FROM User u WHERE u.lastname = :lastname")
@NamedQuery(name = "User.findByActive", 
            query = "SELECT u FROM User u WHERE u.active = :active")
@NamedQuery(name = "User.findByAgeGreaterThan", 
            query = "SELECT u FROM User u WHERE u.age > :age")
public class User extends BaseEntity{
    
    @Id
    @GeneratedValue
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