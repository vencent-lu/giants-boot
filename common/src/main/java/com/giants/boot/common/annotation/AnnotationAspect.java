package com.giants.boot.common.annotation;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * AnnotationAspect TODO
 * date time: 2021/6/6 21:54
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Aspect
@Component
public class AnnotationAspect {

    @Resource
    private GiantsBootCommonProperties giantsBootCommonProperties;

    @Around(value = "@annotation(com.giants.boot.common.annotation.BasePackage)")
    public Object aroundBasePackage(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        this.giantsBootCommonProperties.setBasePackage(methodSignature.getClass().getAnnotation(BasePackage.class).value());
        return pjp.proceed();
    }

    @Around(value = "@annotation(org.springframework.context.annotation.ComponentScan)")
    public Object aroundComponentScan(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        ComponentScan componentScan = methodSignature.getClass().getAnnotation(ComponentScan.class);
        String[] basePackages = componentScan.basePackages();
        for (int i=0; i<basePackages.length; i++) {
            basePackages[i] = basePackages[i].replace("{basePackage}", this.giantsBootCommonProperties.getBasePackage());
        }
        InvocationHandler componentScanProxy = Proxy.getInvocationHandler(componentScan);
        Field field = componentScanProxy.getClass().getDeclaredField("memberValues");
        field.setAccessible(true);
        Map memberValues = (Map) field.get(componentScanProxy);
        memberValues.put("basePackages", basePackages);
        return pjp.proceed();
    }

}
