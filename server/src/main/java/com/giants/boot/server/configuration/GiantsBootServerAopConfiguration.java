package com.giants.boot.server.configuration;

import com.giants.analyse.aop.EnterExecutionTimeProfilerAop;
import com.giants.boot.common.configuration.AbstractAopConfiguration;
import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.cache.core.aop.GiantsCacheAop;
import com.giants.common.lang.reflect.ReflectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * GiantsBootServerAopConfiguration TODO
 * date time: 2021/6/15 18:45
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootServerAopConfiguration extends AbstractAopConfiguration {

    @Bean
    public AspectJPointcutAdvisor createEnterExecutionTimeProfilerAdvisor(BeanFactory beanFactory,
                                                                          GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createAroundPointcutAdvisor(
                "execution(* " + giantsBootCommonProperties.getBasePackage() + "..service..*(..)) " +
                        "or execution(* " + giantsBootCommonProperties.getBasePackage() + "..dao..*(..)) " +
                        "or execution(* " + giantsBootCommonProperties.getBasePackage() + "..api..*(..))",
                "enterExecutionTimeProfilerAop",
                ReflectUtils.getMethod(EnterExecutionTimeProfilerAop.class, "timerProfiler",
                        ProceedingJoinPoint.class), Ordered.HIGHEST_PRECEDENCE, beanFactory);
    }

    @Bean
    public AspectJPointcutAdvisor createGiantsCacheServiceAopAdvisor(BeanFactory beanFactory,
                                                                     GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createAroundPointcutAdvisor(
                "execution(* " + giantsBootCommonProperties.getBasePackage() + "..service..*(..))",
                "giantsCacheServiceAop",
                ReflectUtils.getMethod(GiantsCacheAop.class, "serviceMethodCache",
                        ProceedingJoinPoint.class), Ordered.HIGHEST_PRECEDENCE+1, beanFactory);
    }

    @Bean
    public AspectJPointcutAdvisor createGiantsCacheDaoAopAdvisor(BeanFactory beanFactory,
                                                                     GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createAroundPointcutAdvisor(
                "execution(* " + giantsBootCommonProperties.getBasePackage() + "..dao..*(..))",
                "giantsCacheDaoAop",
                ReflectUtils.getMethod(GiantsCacheAop.class, "serviceMethodCache",
                        ProceedingJoinPoint.class), Ordered.HIGHEST_PRECEDENCE+1, beanFactory);
    }

}
