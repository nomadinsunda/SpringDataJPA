package com.intheeast.demo.service;

import com.intheeast.demo.entity.Person;
import com.intheeast.demo.dto.NamesOnlyDTO;
import com.intheeast.demo.repository.PersonRepository.NamesOnly;
import com.intheeast.demo.repository.PersonRepository.PersonSummary;
import com.intheeast.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    // Create or update a person
    public Person save(Person person) {
        return personRepository.save(person);
    }

    // Find all persons
    public Collection<Person> findAll() {
        return personRepository.findAll();
    }

    // Find a person by ID
    public Person findById(UUID id) {
        Optional<Person> personOptional = personRepository.findById(id);
        return personOptional.orElse(null); // null if not found
    }

    // Update a person
    public Person update(Person person) {
        return personRepository.save(person);
    }

    // Delete a person by ID
    public void deleteById(UUID id) {
        personRepository.deleteById(id);
    }

    // Find persons by last name
    public Collection<NamesOnly> findByLastname(String lastname) {
        return personRepository.findByLastname(lastname);
    }

    // Find persons by first name
    public Collection<PersonSummary> findByFirstname(String firstname) {
        return personRepository.findByFirstname(firstname);
    }

    // Find persons by last name starting with a prefix (using DTO)
    public Collection<NamesOnlyDTO> findByLastnameStartingWith(String prefix) {
        return personRepository.findByLastnameStartingWith(prefix);
    }

    // Dynamic projection for finding persons by last name
    public Collection<?> findByLastname(String lastname, Class<?> type) {
        return personRepository.findByLastname(lastname, type);
    }
}