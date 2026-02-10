package com.intheeast.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.Department;
import com.intheeast.demo.entity.Person;
import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.*;

@SpringBootApplication
public class QueryCreationApplication {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DepartmentRepository depRepository;
	
	@Autowired
	private PersonRepository perRepository;

	public static void main(String[] args) {
		SpringApplication.run(QueryCreationApplication.class, args);
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
            user2.setStatus("world");
            
            User user3 = new User();
            user3.setFirstname("james");
            user3.setLastname("Kim");
            user3.setEmail("helloworld@gmail.com");
            user3.setStatus("world");
            
            User user4 = new User();
            user4.setFirstname("james");
            user4.setLastname("Kim");
            user4.setEmail("goodworld@gmail.com");
            user4.setStatus("world");
            
            User user5 = new User();
            user5.setFirstname("Kris");
            user5.setLastname("choi");
            user5.setEmail("saygoodbye@gmail.com");
            user5.setStatus("world");
            
            User user6 = new User();
            user6.setFirstname("Kris");
            user6.setLastname("choi");
            user6.setEmail("goodnight@gmail.com");
            user6.setStatus("world");

            // User 객체를 데이터베이스에 삽입
            userRepository.saveAll(Arrays.asList(user1, user2, user3, user4, user5, user6));
            System.out.println("Users have been inserted.");
        };
    }
	
	@Bean
    public CommandLineRunner commandLineRunner2() {
		return args -> {
			Department department1 = new Department();
			department1.setName("Biz");
			
			Department department2 = new Department();
			department2.setName("Delivery");
			
			depRepository.saveAll(Arrays.asList(department1, department2));
			
            Person person1 = new Person();
            person1.setFirstname("John");
            person1.setLastname("Doe");
            person1.setDepartment(department1);
            
            Person person2 = new Person();
            person2.setFirstname("John");
            person2.setLastname("Doe");
            person2.setDepartment(department1);

            
            Person person3 = new Person();
            person3.setFirstname("John");
            person3.setLastname("Doe");
            person3.setDepartment(department2);

            
            Person person4 = new Person();
            person4.setFirstname("John");
            person4.setLastname("Doe");
            person4.setDepartment(department2);

            
            Person person5 = new Person();
            person5.setFirstname("John");
            person5.setLastname("Doe");
            person5.setDepartment(department2);

            perRepository.saveAll(Arrays.asList(person1, person2, person3, person4, person5));
        };
	}

}
