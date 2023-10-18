package com.billmanager.restcall.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
public abstract class LogWriter {

     @Pointcut(value = "execution(* com.billmanager.restcall.controller.*(..))")
     public abstract void beforeControllerMethod();
     @Pointcut(value = "execution(* com.billmanager.restcall.service.*(..))")
     public abstract void beforeServiceMethod();

    @Around(value = "beforeControllerMethod() || beforeServiceMethod()")
    public void afterAllMethod() {

    }
}
