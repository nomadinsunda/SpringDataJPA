package com.intheeast.demo.dto;

import com.intheeast.demo.entity.Person;
import com.intheeast.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    // PersonDTO를 User 엔티티로 변환하는 메서드
    public Person toEntity() {
        Person person = new Person();
        person.setFirstname(this.firstname);
        person.setLastname(this.lastname);
        return person;
    }

}
