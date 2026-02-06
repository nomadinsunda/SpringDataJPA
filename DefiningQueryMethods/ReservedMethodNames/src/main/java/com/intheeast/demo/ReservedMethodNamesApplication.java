package com.intheeast.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.domain.User;
import com.intheeast.demo.repository.UserRepository;

@SpringBootApplication
public class ReservedMethodNamesApplication {
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReservedMethodNamesApplication.class, args);
	}
	
	
	@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            // User 객체를 생성하여 데이터베이스에 저장
            User user1 = new User();
            user1.setId(1L);
            user1.setFirstname("John");
            user1.setLastname("Doe");
            user1.setEmail("swseokitec@gmail.com");
            user1.setStatus("hello");

            User user2 = new User();
            user2.setId(2L);
            user2.setFirstname("Jane");
            user2.setLastname("Doe");
            user2.setEmail("intheeast0305@gmail.com");
            user2.setStatus("world");
            
            User user3 = new User();
            user3.setId(3L);
            user3.setFirstname("james");
            user3.setLastname("Kim");
            user3.setEmail("helloworld@gmail.com");
            user3.setStatus("world");
            
            User user4 = new User();
            user4.setId(4L);
            user4.setFirstname("james");
            user4.setLastname("Kim");
            user4.setEmail("goodworld@gmail.com");
            user4.setStatus("world");
            
            User user5 = new User();
            user5.setId(5L);
            user5.setFirstname("Kris");
            user5.setLastname("choi");
            user5.setEmail("saygoodbye@gmail.com");
            user5.setStatus("world");
            
            User user6 = new User();
            user6.setId(6L);
            user6.setFirstname("Kris");
            user6.setLastname("choi");
            user6.setEmail("goodnight@gmail.com");
            user6.setStatus("world");

            // User 객체를 데이터베이스에 삽입
            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6));
            System.out.println("Users have been inserted.");
        };
	}

}
