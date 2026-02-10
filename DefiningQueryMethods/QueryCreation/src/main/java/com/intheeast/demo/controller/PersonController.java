package com.intheeast.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intheeast.demo.dto.PersonDTO;
import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.Person;
import com.intheeast.demo.service.PersonService;

@RestController
@RequestMapping("/persons")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	
	@GetMapping
	public ResponseEntity<List<PersonDTO>> getPersons(@RequestParam String name) {
		return ResponseEntity.ok(personService.findByDepartmentName(name));
	}

}
