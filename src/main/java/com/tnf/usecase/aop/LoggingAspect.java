package com.tnf.usecase.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect @Slf4j
public class LoggingAspect {

//	@Pointcut("within(com.tnf.usecase.service.impl.*) && execution(public * *(Object...))")
//	public void allServiceMethods(Object... args) {}
//
//	@Pointcut("within(com.tnf.usecase.controller.*) && execution(public * *(Object...))")
//	public void allControllerMethods(Object... args) {}
//
//	@Pointcut("allServiceMethods() || allControllerMethods()")
//	public void allControllerAndServiceMethods(Object... args){}
//	
//	@Around("allControllerAndServiceMethods()")
//	public void argAroundAdvice(ProceedingJoinPoint proceedingJoinPoint, Object... args) throws Throwable {
//		StringBuilder sb = new StringBuilder();
//		for(Object x: args)
//			sb.append(x);
//		log.info("arguments: " + sb.toString());
//		log.info("before execution: "+ proceedingJoinPoint.getTarget().getClass().toString());
//		proceedingJoinPoint.proceed();
//		log.info("after execution: "+ proceedingJoinPoint.getTarget().toString());
//		
//	}
	
}
