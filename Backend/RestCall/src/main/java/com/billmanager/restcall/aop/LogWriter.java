package com.billmanager.restcall.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Log4j2
@Aspect
@Component
public abstract class LogWriter {

     @Pointcut(value = "execution(* com.billmanager.restcall.controller.*(..))")
     public abstract void beforeControllerMethod();
     @Pointcut(value = "execution(* com.billmanager.restcall.service.*(..))")
     public abstract void beforeServiceMethod();

    @Before(value = "beforeControllerMethod() || beforeServiceMethod()")
    public void beforeAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = joinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        log.info("Execution time of " + className + "." + methodName + " "
                + ":: " + stopWatch.getTotalTimeMillis() + " ms");
        log.info("method name :"+methodName+ " request : "+result);
    }

    @AfterReturning(value = "beforeControllerMethod() || beforeServiceMethod()",returning = "value")
    public void afterAllMethod(ProceedingJoinPoint proceedingJoinPoint,Object value) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        stopWatch.stop();

        //Log method execution time
        log.info("Execution time of " + className +"method name :"+methodName+ " request : "+value  + ":: " + stopWatch.getTotalTimeMillis() + " ms");
    }
}
