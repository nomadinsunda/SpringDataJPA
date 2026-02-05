package com.intheeast.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

@SpringBootApplication
public class QueryLookupStrategiesApplication {
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(QueryLookupStrategiesApplication.class, args);
	}
	
	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // User 객체를 생성하여 데이터베이스에 저장
            User user1 = new User();
            user1.setFirstname("John");
            user1.setLastname("Doe");
            user1.setEmail("swseokitec@gmail.com");
            user1.setStatus("hello");

            User user2 = new User();
            user2.setFirstname("Jane");
            user2.setLastname("Doe");
            user2.setEmail("intheeast0305@gmail.com");
            user1.setStatus("world");

            // User 객체를 데이터베이스에 삽입
            userRepository.saveAll(Arrays.asList(user1, user2));
            System.out.println("Users have been inserted.");
        };
    }

}
