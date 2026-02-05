package com.intheeast.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.entity.User;
import com.intheeast.demo.repository.UserRepository;

@SpringBootApplication
public class IndexApplication {
	
	@Value("${first.names}")
    private String firstNames;

    @Value("${last.names}")
    private String lastNames;

    @Value("${ages}")
    private String ages;

    @Value("${active}")
    private String activeStatuses;

    @Value("${email.numbers}")
    private String emailNumbers;

    @Value("${domains}")
    private String domains;

	public static void main(String[] args) {
		SpringApplication.run(IndexApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            Random random = new Random();
            List<String> firstNameList = Arrays.asList(firstNames.split(","));
            List<String> lastNameList = Arrays.asList(lastNames.split(","));
            List<Integer> ageList = Arrays.stream(ages.split(","))
                    .map(Integer::parseInt)
                    .toList();
            List<Boolean> activeStatusList = Arrays.stream(activeStatuses.split(","))
                    .map(Boolean::parseBoolean)
                    .toList();
            List<String> emailNumberList = Arrays.asList(emailNumbers.split(",")); // 이메일 번호 리스트
            List<String> domainList = Arrays.asList(domains.split(",")); // 도메인 리스트

            Set<String> existingEmails = new HashSet<>();
            List<User> users = new ArrayList<>();

            for (int i = 0; i < 100; i++) {
                String firstName = firstNameList.get(random.nextInt(firstNameList.size()));
                String lastName = lastNameList.get(random.nextInt(lastNameList.size()));
                Integer age = ageList.get(random.nextInt(ageList.size()));
                Boolean activeStatus = activeStatusList.get(random.nextInt(activeStatusList.size()));

                // 랜덤 이메일 생성 (도메인과 숫자 포함)
                String emailNumber = emailNumberList.get(random.nextInt(emailNumberList.size()));
                String domain = domainList.get(random.nextInt(domainList.size()));
                String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + 
                               emailNumber + "@" + domain;  // 이메일 형식

                // 중복 이메일 확인
                while (existingEmails.contains(email)) {
                    emailNumber = emailNumberList.get(random.nextInt(emailNumberList.size())); // 중복될 경우 다른 숫자 선택
                    email = firstName.toLowerCase() + "." + lastName.toLowerCase() +
                            emailNumber + "@" + domain;  // 중복될 경우 다시 생성
                }

                existingEmails.add(email);
                users.add(new User(firstName, lastName, email, age, activeStatus));
            }

            userRepository.saveAll(users);
        };
    }


}
