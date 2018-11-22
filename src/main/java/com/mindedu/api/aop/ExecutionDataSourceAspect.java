package com.mindedu.api.aop;

import com.mindedu.api.define.datasource.ContextHolder;
import com.mindedu.api.define.datasource.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(value = 1)
public class ExecutionDataSourceAspect {

    final static Logger logger = LoggerFactory.getLogger(ExecutionDataSourceAspect.class);

    @Pointcut("execution(* com.mindedu.api..*Service.*(..))")
    public void serviceDB() {}

    @Pointcut("@annotation(javax.annotation.PostConstruct)")
    public void postConstruct() {}

    @Around("(serviceDB() || postConstruct())")
    public Object doManagerProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("############## do data source aspect ##############");
        final String methodName = joinPoint.getSignature().getName();
        final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }

        DataSource dataSource = method.getAnnotation(DataSource.class);
        if (dataSource != null) {
            ContextHolder.setDataSourceType(dataSource.value());
            logger.info("data source >> " + dataSource.value());
        }
        if(dataSource == null){
            logger.info("default data source.....");
        }
        Object returnValue = joinPoint.proceed();
        ContextHolder.clearDataSourceType();

        return returnValue;
    }
}
