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

import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

@SpringBootApplication
public class StreamingQueryResultsApplication {
	/*
	 at application.yml
	 spring:
  		config:
    		import: classpath:names.properties

	 */
	@Value("${first.names}")
    private String firstNames;

    @Value("${last.names}")
    private String lastNames;

	public static void main(String[] args) {
		SpringApplication.run(StreamingQueryResultsApplication.class, args);
	}
	
	
	@Bean
    CommandLineRunner init(UserRepository userRepository) {
    	return args -> {
            Random random = new Random();
            List<String> firstNameList = Arrays.asList(firstNames.split(","));
            List<String> lastNameList = Arrays.asList(lastNames.split(","));
            List<User> users = new ArrayList<>();

            for (int i = 0; i < 50; i++) {
                String firstName = firstNameList.get(random.nextInt(firstNameList.size()));
                String lastName = lastNameList.get(random.nextInt(lastNameList.size()));
                users.add(new User(firstName, lastName));
            }

            userRepository.saveAll(users);
        };
    }
	
//	@Bean
//  CommandLineRunner init(UserRepository repository) {
//      return args -> {
//          List<User> users = IntStream.range(0, 50)
//              .mapToObj(i -> new User("User" + i, "Lastname" + i))
//              .collect(Collectors.toList());
//              
//          repository.saveAll(users);
//      };
//  }

}
