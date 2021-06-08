package com.giants.boot.gateway.configuration;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.common.lang.reflect.ReflectUtils;
import com.giants.web.springmvc.aop.ControllerValidationAop;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * AopConfiguration TODO
 * date time: 2021/6/5 12:00
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootGatewayAopConfiguration extends AbstractAopConfiguration {

    @Resource
    private GiantsBootCommonProperties giantsBootCommonProperties;

    @Bean
    public AspectJPointcutAdvisor createValidatorControllerMethodAdvisor(BeanFactory beanFactory)
            throws NoSuchMethodException {
        return this.createBeforePointcutAdvisor("execution(* " + this.giantsBootCommonProperties.getBasePackage() +
                "..controller..*(*,org.springframework.validation.BindingResult,..))",
                "controllerValidationAop", ReflectUtils.getMethod(ControllerValidationAop.class,
                        "validate", JoinPoint.class), 99, beanFactory);
    }

}
