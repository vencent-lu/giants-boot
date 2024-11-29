package com.giants.boot.aggregator.configuration;

import com.giants.analyse.aop.EnterExecutionTimeProfilerAop;
import com.giants.boot.common.configuration.AbstractAopConfiguration;
import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.cache.core.aop.GiantsCacheAop;
import com.giants.common.lang.reflect.ReflectUtils;
import com.giants.web.springmvc.aop.ControllerValidationAop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * GiantsBootAggregatorAopConfiguration TODO
 * date time: 2024/11/29 15:33
 * Copyright 2024 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.5.0
 */
@Configuration
public class GiantsBootAggregatorAopConfiguration extends AbstractAopConfiguration {

    @Bean
    public AspectJPointcutAdvisor createValidatorControllerMethodAdvisor(BeanFactory beanFactory,
                                                                         GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createBeforePointcutAdvisor("execution(* " + giantsBootCommonProperties.getBasePackage() +
                "..controller..*(*,org.springframework.validation.BindingResult,..))",
                "controllerValidationAop", ReflectUtils.getMethod(ControllerValidationAop.class,
                        "validate", JoinPoint.class), Ordered.HIGHEST_PRECEDENCE+2, beanFactory);
    }

    @Bean
    public AspectJPointcutAdvisor createEnterExecutionTimeProfilerAdvisor(BeanFactory beanFactory,
                                                                          GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createAroundPointcutAdvisor(
                "execution(* " + giantsBootCommonProperties.getBasePackage() + "..controller..*(..)) " +
                        "or execution(* " + giantsBootCommonProperties.getBasePackage() + "..api..*(..))",
                "enterExecutionTimeProfilerAop",
                ReflectUtils.getMethod(EnterExecutionTimeProfilerAop.class, "timerProfiler",
                        ProceedingJoinPoint.class), Ordered.HIGHEST_PRECEDENCE, beanFactory);
    }

    @Bean
    public AspectJPointcutAdvisor createGiantsCacheApiAopAdvisor(BeanFactory beanFactory,
                                                                 GiantsBootCommonProperties giantsBootCommonProperties)
            throws NoSuchMethodException {
        return this.createAroundPointcutAdvisor(
                "execution(* " + giantsBootCommonProperties.getBasePackage() + "..api..*(..))",
                "giantsCacheApiAop",
                ReflectUtils.getMethod(GiantsCacheAop.class, "serviceMethodCache",
                        ProceedingJoinPoint.class), Ordered.HIGHEST_PRECEDENCE+1, beanFactory);
    }

}
