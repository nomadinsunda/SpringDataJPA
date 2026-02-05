package com.intheeast.demo.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
//@DynamicUpdate
@Entity
public class User extends BaseEntity{
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String firstname;
    private String lastname;
    
    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    
    private String status;
    
    public User(String firstname, String lastname, String email, String status) {
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.email = email;  
    	this.status = status;
    }

}
