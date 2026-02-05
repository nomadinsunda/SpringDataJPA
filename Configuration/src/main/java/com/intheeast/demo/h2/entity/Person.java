package com.intheeast.demo.h2.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
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
public class Person {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String firstname;
	private String lastname;
	
	@Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
	
	@Transient
	private String fullname;
	
	public Person(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}


}