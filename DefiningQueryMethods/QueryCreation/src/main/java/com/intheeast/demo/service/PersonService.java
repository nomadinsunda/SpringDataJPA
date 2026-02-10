package com.intheeast.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intheeast.demo.dto.PersonDTO;
import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.entity.Person;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	public List<PersonDTO> findByDepartmentName(String name) {
		
		List<Person> persons = personRepository.findByDepartmentName(name);
	    
        return persons.stream()
                    .map(PersonDTO::fromEntity) // 
                    .collect(Collectors.toList());
	}

}
