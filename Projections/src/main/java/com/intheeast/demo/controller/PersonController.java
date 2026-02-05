package com.intheeast.demo.controller;

import com.intheeast.demo.entity.Person;
import com.intheeast.demo.service.PersonService;
import com.intheeast.demo.repository.PersonRepository.NamesOnly;
import com.intheeast.demo.repository.PersonRepository.PersonSummary;
import com.intheeast.demo.dto.NamesOnlyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.save(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Collection<Person>> getAllPersons() {
        Collection<Person> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<Person> getPersonById(@RequestBody UUIDRequest uuidRequest) {
        Person person = personService.findById(uuidRequest.getId());
        return person != null ? new ResponseEntity<>(person, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id")
    public ResponseEntity<Person> updatePerson(@RequestBody UUIDRequest uuidRequest, @RequestBody Person person) {
        person.setId(uuidRequest.getId());
        Person updatedPerson = personService.update(person);
        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deletePerson(@RequestBody UUIDRequest uuidRequest) {
        personService.deleteById(uuidRequest.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/lastname/{lastname}")
    public ResponseEntity<Collection<NamesOnly>> getPersonsByLastname(@PathVariable String lastname) {
        Collection<NamesOnly> namesOnly = personService.findByLastname(lastname);
        return new ResponseEntity<>(namesOnly, HttpStatus.OK);
    }

    @GetMapping("/firstname/{firstname}")
    public ResponseEntity<Collection<PersonSummary>> getPersonsByFirstname(@PathVariable String firstname) {
        Collection<PersonSummary> persons = personService.findByFirstname(firstname);
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping("/lastname/starting-with/{prefix}")
    public ResponseEntity<Collection<NamesOnlyDTO>> getPersonsByLastnameStartingWith(@PathVariable String prefix) {
        Collection<NamesOnlyDTO> namesOnlyDTOs = personService.findByLastnameStartingWith(prefix);
        return new ResponseEntity<>(namesOnlyDTOs, HttpStatus.OK);
    }

    // Dynamic projection endpoint
    @GetMapping("/lastname/{lastname}/projection")
    public ResponseEntity<Collection<?>> getPersonsByLastnameWithProjection(
            @PathVariable String lastname,
            @RequestParam Class<?> type) {
        Collection<?> projections = personService.findByLastname(lastname, type);
        return new ResponseEntity<>(projections, HttpStatus.OK);
    }
}

// UUIDRequest DTO
class UUIDRequest {
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}