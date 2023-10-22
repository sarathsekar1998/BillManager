package com.billmanager.restcall.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Log4j2
public class LogWriter {

    @Pointcut("execution(* com.billmanager.restcall.service.*.*(..)) && args(value)")
    public void beforeAllServiceMethod(Object value){}

    @AfterReturning(value = "beforeAllServiceMethod(request)",returning = "response")
    public void logger1(JoinPoint joinPoint,Object response,Object request){
        log.info(joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName() +"-- response :"+response);
    }
    @Before(value ="beforeAllServiceMethod(request)")
    public void logger(JoinPoint joinPoint,Object request){
        log.info(joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName() +"-- request :"+request);
    }

    @AfterThrowing(value ="beforeAllServiceMethod(request)",throwing = "value")
    public void logger2(JoinPoint joinPoint,Exception value,Object request){
        log.error(joinPoint.getSignature().getDeclaringType()+"."+joinPoint.getSignature().getName() +"-- exception :"+value);
    }

}
