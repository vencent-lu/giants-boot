package com.giants.boot.gateway.configuration.annotation;

import com.giants.boot.common.annotation.BasePackage;
import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cloud.openfeign.EnableFeignClients;
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

    @Around(value = "@annotation(org.springframework.cloud.openfeign.EnableFeignClients)")
    public Object aroundEnableFeignClients(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        EnableFeignClients enableFeignClients = methodSignature.getClass().getAnnotation(EnableFeignClients.class);
        String[] basePackages = enableFeignClients.basePackages();
        for (int i=0; i<basePackages.length; i++) {
            basePackages[i] = basePackages[i].replace("{basePackage}", this.giantsBootCommonProperties.getBasePackage());
        }
        InvocationHandler enableFeignClientsProxy = Proxy.getInvocationHandler(enableFeignClients);
        Field field = enableFeignClientsProxy.getClass().getDeclaredField("memberValues");
        field.setAccessible(true);
        Map memberValues = (Map) field.get(enableFeignClientsProxy);
        memberValues.put("basePackages", basePackages);
        return pjp.proceed();
    }
}
