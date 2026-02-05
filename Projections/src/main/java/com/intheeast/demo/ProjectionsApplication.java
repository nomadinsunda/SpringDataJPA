package com.intheeast.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.Address;
import com.intheeast.demo.entity.Person;
import com.intheeast.demo.repository.PersonRepository;

@SpringBootApplication
public class ProjectionsApplication {
	
	@Value("${first.names}")
    private String firstNames;

    @Value("${last.names}")
    private String lastNames;

    @Value("${zip.codes}")
    private String zipCodes;

    @Value("${cities}")
    private String cities;

    @Value("${streets}")
    private String streets;

	public static void main(String[] args) {
		SpringApplication.run(ProjectionsApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(PersonRepository personRepository) {
        return args -> {
            Random random = new Random();

            // Split the values from properties
            List<String> firstNameList = Arrays.asList(firstNames.split(","));
            List<String> lastNameList = Arrays.asList(lastNames.split(","));
            List<String> zipCodeList = Arrays.asList(zipCodes.split(","));
            List<String> cityList = Arrays.asList(cities.split(","));
            List<String> streetList = Arrays.asList(streets.split(","));

            List<Person> persons = new ArrayList<>();

            // Generating random 50 persons
            for (int i = 0; i < 50; i++) {
                String firstName = firstNameList.get(random.nextInt(firstNameList.size()));
                String lastName = lastNameList.get(random.nextInt(lastNameList.size()));
                String zipCode = zipCodeList.get(random.nextInt(zipCodeList.size()));
                String city = cityList.get(random.nextInt(cityList.size()));
                String street = streetList.get(random.nextInt(streetList.size()));

                // Creating Address object
                Address address = new Address(zipCode, city, street);

                // Creating new Person object with Address
                Person person = new Person(firstName, lastName, address);

                persons.add(person);
            }

            // Save all persons to the database
            personRepository.saveAll(persons);
        };
    }
	

}
