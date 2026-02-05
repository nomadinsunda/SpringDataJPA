package com.intheeast.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intheeast.demo.dto.PersonDTO;
import com.intheeast.demo.h2.entity.Person;
import com.intheeast.demo.service.PersonService;



@RestController
@RequestMapping("/persons")
public class PersonController {
	
		@Autowired
	    private PersonService personService;

	    @PostMapping
	    public PersonDTO createUser(@RequestBody PersonDTO personDTO) {
	        Person person = new Person(personDTO.getFirstname(), 
	        		personDTO.getLastname());
	        return personService.savePerson(person);
	        
	    }

	    // 모든 유저 조회
	    @GetMapping
	    public List<PersonDTO> getAllPersons() {
	        return personService.findAll();
	    }

	    
	    // 유저 조회 by ID : /users/by-id?id=1
	    @GetMapping("/by-id")
	    public PersonDTO getPersonById(@RequestParam Long id) {
	        return personService.findById(id);
	    }

	    // 특정 성을 가진 유저 수 카운트
	    @GetMapping("/count/{lastname}")
	    public long countUsersByLastname(@PathVariable String lastname) {
	        return personService.countByLastname(lastname);
	    }   

	    // 유저 업데이트
	    @PutMapping("/{id}")
	    public void updatePerson(@RequestBody PersonDTO personDTO) {
	    	personService.updatePerson(personDTO.getFirstname(), 
	    			personDTO.getLastname());
	    }

}
