package com.example.shop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);
    private static final String SERVICES_POINTCUT = "within(com.example.shop.services.*)";

    @Around(SERVICES_POINTCUT)
    public Object logAroundExec(ProceedingJoinPoint pjp) throws Throwable {
        Object proceed = pjp.proceed();
        String loggingMessage = constructLogMsg(pjp);
        if (proceed != null) {
            LOG.info("{};\nReturned Values: {}\n", loggingMessage, proceed);
        } else {
            LOG.info("{};\nReturned Values: No Values\n", loggingMessage);
        }
        return proceed;
    }

    @AfterThrowing(pointcut = SERVICES_POINTCUT, throwing = "e")
    public void logAfterException(JoinPoint jp, Exception e) {
        String loggingMessage = constructLogMsg(jp);
        String exceptionMessage = e.toString();
        LOG.error("Exception during: {};\nException: {}", loggingMessage,  exceptionMessage);
    }

    private String constructLogMsg(JoinPoint jp) {
        String args = Arrays
                .stream(jp.getArgs())
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        return "\nClass: " + method.getDeclaringClass().getSimpleName() +
                ";\nMethod: " + method.getName() +
                ";\nArguments: " + args;
    }
}


