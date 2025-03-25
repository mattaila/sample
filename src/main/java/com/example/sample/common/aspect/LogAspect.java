package com.example.sample.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Around("@within(org.springframework.stereotype.Controller)")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        
        try{
            
            log.info("method start:" + joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("method end:" + joinPoint.getSignature());

            return result;

        } catch(Exception ex) {
            log.error("error:", ex);
            throw ex;
        }
    }
}
