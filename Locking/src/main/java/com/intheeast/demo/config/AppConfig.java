package com.intheeast.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.intheeast.demo.aop.TransactionAspect;

@Configuration
@EnableJpaRepositories(basePackages="com.intheeast.demo.repository")
@EnableAspectJAutoProxy
public class AppConfig {
	
	@Bean
    public TransactionAspect transactionAspect() {
        return new TransactionAspect();
    }

}
