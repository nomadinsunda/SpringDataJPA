package com.intheeast.demo;

import com.intheeast.demo.aop.TransactionAspect;
import com.intheeast.demo.dto.UserDTO;
import com.intheeast.demo.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Order(2)
public class LockTestRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LockTestRunner.class);

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        
        // 스레드를 이용한 동시성 테스트
        Runnable task1 = () -> {
            try {
                logger.info("Task 1 started");
//                UserDTO user1 = userService.findUserByIdWithLock(1L);
                userService.findAllUsers();
                
                // 작업 지연을 추가하여 잠금의 효과를 확인
                Thread.sleep(2000);

                logger.info("Task 1 - Retrived User");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Runnable task2 = () -> {
            try {
            	logger.info("Task 2 started");
//            	UserDTO user2 = userService.findUserByIdWithLock(1L);
                userService.findAllUsers();

                
                // 작업 지연을 추가하여 잠금의 효과를 확인
                Thread.sleep(2000);                

                logger.info("Task 2 - Retrived User");
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 스레드 시작
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Lock test finished");
    }
}