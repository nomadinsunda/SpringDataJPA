package com.intheeast.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intheeast.demo.dto.PersonDTO;
import com.intheeast.demo.h2.entity.Person;
import com.intheeast.demo.h2.repository.PersonRepository;



@Service
public class PersonService {
	
	private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public long countByLastname(String lastname) {
        return personRepository.countByLastname(lastname);
    }
   

    @Transactional
    public PersonDTO savePerson(Person person) {
    	Person savedPerson = personRepository.save(person);
        return PersonDTO.fromEntity(savedPerson); 
    }

    public List<PersonDTO> findAll() {
        List<Person> users = (List<Person>) personRepository.findAll();
        return users.stream()
                    .map(PersonDTO::fromEntity) // User -> UserDTO 변환
                    .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) {
        Person person = personRepository.findById(id).orElse(null);
        return PersonDTO.fromEntity(person); // User -> UserDTO 변환 (null 가능성 처리)
    }


    @Transactional
    public void updatePerson(String firstname, String lastname) {
    	List<Person> persons = personRepository.findByLastname(lastname);
        
        
    }
	

}
