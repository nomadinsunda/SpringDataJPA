package com.intheeast.demo.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class SimpleLoggingTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleLoggingTest.class);

    public void logTest() {
        logger.info("Simple Logging Test: INFO");
        logger.debug("Simple Logging Test: DEBUG");
    }
    
    @PostConstruct
    public void init() {
        logger.info("빈이 생성되고 나서 호출됨");
        logTest();  // 빈이 등록된 후 logTest 메서드 호출
    }
}