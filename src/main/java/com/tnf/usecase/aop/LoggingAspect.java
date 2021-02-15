package com.tnf.usecase.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect @Slf4j
public class LoggingAspect {

	@Pointcut("within(com.tnf.usecase.service.impl.*) && execution(public * *(..))")
	public void allServiceMethods() {}

	@Pointcut("within(com.tnf.usecase.controller.*) && execution(public * *(..))")
	public void allControllerMethods() {}

	@Pointcut("allServiceMethods() || allControllerMethods()")
	public void allControllerAndServiceMethods(){}
	
	@Before("allControllerAndServiceMethods()")
	public void commonBeforeAdvice(JoinPoint joinPoint){
		log.info("before execution: class: {} ,method : {}, args : {}",joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName(),
				Arrays.stream(joinPoint.getArgs()).reduce("",(a,b)->a.toString()+","+b.toString()));
	}
	
	@AfterReturning(pointcut = "allControllerAndServiceMethods()",returning = "returnedObj")
	public void commonAfterAdvice(JoinPoint joinPoint,Object returnedObj){
		log.info("after execution: return value: {}",returnedObj.toString());
	}
	
	@AfterThrowing(pointcut = "allControllerAndServiceMethods()",throwing = "ex")
	public void commmonExceptionAdvice(JoinPoint joinPoint,RuntimeException ex){
		log.error("after exception: ",ex.getMessage());
	}
	
}
