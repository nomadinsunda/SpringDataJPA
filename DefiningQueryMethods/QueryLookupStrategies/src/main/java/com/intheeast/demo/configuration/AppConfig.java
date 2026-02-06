package com.intheeast.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;


@Configuration
@EnableJpaRepositories(basePackages={"com.intheeast.demo"}, queryLookupStrategy=Key.CREATE_IF_NOT_FOUND)
public class AppConfig {

}
