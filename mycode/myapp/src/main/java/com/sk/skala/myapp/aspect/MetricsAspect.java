package com.sk.skala.myapp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Configuration 기반 UserServiceProxy를 대체: execution 포인트컷으로 UserService의 모든 메소드를 감싼다.
@Slf4j
@Aspect
@Component
public class MetricsAspect {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    @Before("execution(* com.sk.skala.myapp.service.UserService.*(..))")
    public void logControllerStart(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("[AOP before] 시작 - 대상: {}, 메소드: {}", targetName, methodName);
    }

    @Around("execution(* com.sk.skala.myapp.service.UserService.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime startTime = LocalDateTime.now();
        long startMillis = System.currentTimeMillis();
        log.info("[AOP Around] {} 메소드 시작: {}", methodName, startTime.format(TIME_FORMATTER));
        try {
            return joinPoint.proceed();
        } finally {
            long elapsed = System.currentTimeMillis() - startMillis;
            LocalDateTime endTime = LocalDateTime.now();
            log.info("[AOP Around] {} 메소드 종료: {} | 총 소요 시간: {} ms",
                    methodName, endTime.format(TIME_FORMATTER), elapsed);
        }
    }
}
