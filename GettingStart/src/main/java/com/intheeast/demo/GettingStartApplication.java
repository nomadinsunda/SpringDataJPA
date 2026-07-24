package com.intheeast.demo;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.Home;
import com.intheeast.demo.entity.Person;
import com.intheeast.demo.repository.HomeRepository;
import com.intheeast.demo.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class GettingStartApplication {

	public static void main(String[] args) {
		SpringApplication.run(GettingStartApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runnerForPersonOneInstance(PersonRepository repository) {
		
		return args -> {
			Person person = new Person();
			person.setName("John");
//			person.setCreatedBy(null);
//			person.setLastModifiedBy(null);
//			person.setMobile(null);
			
			// EntityManager.persist(person);
			// tx.commit

			/*
			 insert into person (creation_date, last_modified_date, created_by, last_modified_by, name, mobile) 
    		 	values 
        		 	(?, ?, ?, ?, ?, ?)
			 */
			/*
			 insert into person (creation_date, last_modified_date, name) 
    			values
        			(?, ?, ?)
			 */
			repository.save(person);
			////////////////////////////
			
			Person saved = repository.
					findById(person.getId()).
					orElseThrow(NoSuchElementException::new);
			
			log.info("result:ID:" + saved.getId());
			
			saved.setName("kris");
			repository.save(saved);
			
		
	    };
	}
	
	@Bean
	CommandLineRunner runnerForHomeOneInstance(HomeRepository repository) {
		
		return args -> {
			Home home = new Home();
			home.setName("우리집");
			
			// EntityManager.persist(person);
			// tx.commit

			repository.save(home);
			////////////////////////////
			
			Home saved = repository.
					findById(home.getId()).
					orElseThrow(NoSuchElementException::new);
			
			log.info("result:ID:" + saved.getId());
			
			saved.setName("너네집");
			repository.save(saved);			
		
	    };
	}

}
