package com.intheeast.demo.exceptions;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class TransactionExceptionAdvisor {

    /**
     * @Transactional 이 적용된 메서드에서 예외 발생 시 처리
     */
    @AfterThrowing(
        pointcut = "@annotation(transactional)",
        throwing = "ex"
    )
    public void handleTransactionalException(
            JoinPoint joinPoint,
            Transactional transactional,
            Throwable ex
    ) {
        log.error(
            "[TX-EXCEPTION] method={}, rollbackFor={}, exception={}",
            joinPoint.getSignature(),
            transactional.rollbackFor(),
            ex.getClass().getSimpleName(),
            ex
        );

        // 필요 시
        // 1. 슬랙 알림
        // 2. 공통 에러 코드 변환
        // 3. 감사 로그 저장
    }
}
