package com.example.shop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void springBeanPointcut() {
    }

    @Pointcut("within(com.example.shop.services..*)")
    public void applicationPackagePointcut() {
    }

    @Around("applicationPackagePointcut() && springBeanPointcut()")
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

    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
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

    @Pointcut("execution(public * com.example.shop.controllers..*.*(..))")
    public void pointCut(){
    }

    @Before("pointCut()")
    public void beforeMethod(JoinPoint joinPoint){
        ServletRequestAttributes servletRequestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (servletRequestAttributes == null) {
            return;
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String jsonString = (String) JSONObject.wrap(Arrays.toString(joinPoint.getArgs()));

        LOG.info ("\n--- REQUEST INFORMATION --------\n" +
                "Request URI: {}\n" +
                "Controller: {}\n" +
                "Method type: {}\n" +
                "Request Parameters: {}\n" +
                "--- REQUEST INFORMATION --------\n", requestURI,
                joinPoint.getTarget().getClass().getSimpleName(), method, jsonString);
    }
}


