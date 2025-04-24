package com.org.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Component @Aspect
public class Logging {

    @Pointcut("execution(* com.org..controller..*(..)) || execution(* com.org..service..*(..))")
    public void appPckgPointCut () {}

    @Before("appPckgPointCut()")
    public void logMethodCall (JoinPoint jp) {
        String className = jp.getTarget().getClass().getSimpleName();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        log.info("METHOD CALLED :: {} :: {} :: WITH ARGS :: {}" , className,methodName,args);
    }

    @AfterReturning(pointcut = "appPckgPointCut()" , returning = "result")
    public void logMethodReturn (JoinPoint jp, Object result) {
        String className = jp.getTarget().getClass().getSimpleName();
        String methodName = jp.getSignature().getName();
        log.info("METHOD EXITED :: {} :: {} :: RETURNING :: {}" , className, methodName, result);
    }
}
