package com.intheeast.demo;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

import jakarta.persistence.EntityManager;

@SpringBootApplication
public class PersistingEntitiesApplication {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EntityManager entityManagenr;
	
	public static void main(String[] args) {
		SpringApplication.run(PersistingEntitiesApplication.class, args);
	}

	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // User 객체를 생성하여 데이터베이스에 저장
            User user1 = new User();
            user1.setFirstname("John");
            user1.setLastname("Doe");
            user1.setEmail("swseokitec@gmail.com");

            User user2 = new User();
            user2.setFirstname("Jane");
            user2.setLastname("Doe");
            user2.setEmail("intheeast0305@gmail.com");

            // User 객체를 데이터베이스에 삽입
            userRepository.saveAll(Arrays.asList(user1, user2));
            System.out.println("Users have been inserted.");
            
            entityManagenr.clear(); // cache clear
            
            Optional<User> optGotUser1 = userRepository.findById(user1.getId());
            User gotUser1 = optGotUser1.get();
            Optional<User> optGotUser2 = userRepository.findById(user2.getId());
            User gotUser2 = optGotUser2.get();
            
            
            int a = 0;


        };
    }
}
