package com.intheeast.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.intheeast.demo.aop.TransactionAspect;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
	
	@Bean
    public TransactionAspect transactionAspect() {
        return new TransactionAspect();
    }

}
