package com.intheeast.demo.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect {

	
    private static final Logger logger = LoggerFactory.getLogger(TransactionAspect.class);

    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void beforeTransaction() {
        logger.info("트랜잭션 시작");
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void afterTransaction() {
        logger.info("트랜잭션 종료");
    }

    @AfterThrowing(pointcut = "@annotation(org.springframework.transaction.annotation.Transactional)", throwing = "exception")
    public void afterThrowingTransaction(Exception exception) {
        logger.error("트랜잭션 중 예외 발생: {}", exception.getMessage());
    }
}