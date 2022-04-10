package com.example.shop.aspect;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import lombok.var;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    private static final String POINTCUT = "within(com.example.shop.services.*)";

    @Around(POINTCUT)
    @SneakyThrows
    public Object logAroundExec(ProceedingJoinPoint pjp) {
        var proceed = pjp.proceed();
        if (proceed != null) {
            log.info("{}, {}",constructLogMsg(pjp), proceed.toString());
        } else {
            log.info(constructLogMsg(pjp));
        }
        return proceed;
    }

    @AfterThrowing(pointcut = POINTCUT, throwing = "e")
    public void logAfterException(JoinPoint jp, Exception e) {
        log.error("Exception during: {} with ex: {}", constructLogMsg(jp),  e.toString());
    }

    private String constructLogMsg(JoinPoint jp) {
        var args = Arrays.stream(jp.getArgs()).map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        return "Class: " + method.getDeclaringClass().getSimpleName() + ",\nMethod: " + method.getName() +
                ", " + args + "\n";
    }
}


