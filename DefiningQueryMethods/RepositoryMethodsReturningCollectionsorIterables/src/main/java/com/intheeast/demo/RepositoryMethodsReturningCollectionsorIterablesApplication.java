package com.intheeast.demo;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.intheeast.demo.repository.ProductRepository;
import com.intheeast.demo.entity.*;


@SpringBootApplication
public class RepositoryMethodsReturningCollectionsorIterablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryMethodsReturningCollectionsorIterablesApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(ProductRepository repository) {
        return args -> {
            repository.save(new Product("Apple iPhone", new BigDecimal("999.99")));
            repository.save(new Product("Samsung Galaxy", new BigDecimal("799.99")));
            repository.save(new Product("Apple Watch", new BigDecimal("399.99")));
        };
    }

}
