package com.intheeast.demo.dto;

import com.intheeast.demo.h2.entity.Person;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PersonDTO {
	
	private String firstname;
    private String lastname;    
    
    public static PersonDTO fromEntity(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonDTO(person.getFirstname(), 
        		person.getLastname());
    }

    public Person toEntity() {
    	Person person = new Person();
    	person.setFirstname(this.firstname);
    	person.setLastname(this.lastname);

        return person;
    }

}
