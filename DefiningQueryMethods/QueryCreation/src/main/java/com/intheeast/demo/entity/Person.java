package com.intheeast.demo.entity;

import static jakarta.persistence.GenerationType.AUTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person extends BaseEntity{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String firstname;
    private String lastname;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;
	

}
