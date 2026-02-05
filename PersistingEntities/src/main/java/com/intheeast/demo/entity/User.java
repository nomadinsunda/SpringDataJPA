package com.intheeast.demo.entity;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
//@DynamicUpdate
@Entity
public class User extends AbstractEntity<Long>{
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
    private String firstname;
    private String lastname;
    
    @Column(unique = true)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    
//    @Transient
//    @Setter(AccessLevel.NONE)
//    private boolean isNew = true;

//    @PrePersist
//    @PostLoad
//    void markNotNew() {
//        this.isNew = false; // 엔티티가 로드된 후 isNew 플래그를 false로 설정
//    }

    
    public User(String firstname, String lastname, String email) {
    	this.firstname = firstname;
    	this.lastname = lastname;
    	this.email = email;    	
    }

}
