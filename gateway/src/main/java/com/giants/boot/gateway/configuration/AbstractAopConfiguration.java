package com.giants.boot.gateway.configuration;

import org.springframework.aop.aspectj.AspectJAroundAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.AspectJMethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJPointcutAdvisor;
import org.springframework.aop.config.SimpleBeanFactoryAwareAspectInstanceFactory;
import org.springframework.beans.factory.BeanFactory;

import java.lang.reflect.Method;

/**
 * AbstractAopConfiguration TODO
 * date time: 2021/6/5 11:55
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public abstract class AbstractAopConfiguration {

    protected AspectJPointcutAdvisor createAroundPointcutAdvisor(String expression, String aspectBeanName,
                                                               Method aspectJAroundAdviceMethod, int order,
                                                               BeanFactory beanFactory) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        SimpleBeanFactoryAwareAspectInstanceFactory aspectInstanceFactory =
                new SimpleBeanFactoryAwareAspectInstanceFactory();
        aspectInstanceFactory.setAspectBeanName(aspectBeanName);
        aspectInstanceFactory.setBeanFactory(beanFactory);

        AspectJAroundAdvice aroundAdvice =
                new AspectJAroundAdvice(aspectJAroundAdviceMethod, pointcut, aspectInstanceFactory);

        AspectJPointcutAdvisor aspectJPointcutAdvisor = new AspectJPointcutAdvisor(aroundAdvice);
        aspectJPointcutAdvisor.setOrder(order);
        return aspectJPointcutAdvisor;
    }

    protected AspectJPointcutAdvisor createBeforePointcutAdvisor(String expression, String aspectBeanName,
                                                               Method aspectJBeforeAdviceMethod, int order,
                                                               BeanFactory beanFactory) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);

        SimpleBeanFactoryAwareAspectInstanceFactory aspectInstanceFactory =
                new SimpleBeanFactoryAwareAspectInstanceFactory();
        aspectInstanceFactory.setAspectBeanName(aspectBeanName);
        aspectInstanceFactory.setBeanFactory(beanFactory);

        AspectJMethodBeforeAdvice beforeAdvice =  new AspectJMethodBeforeAdvice(aspectJBeforeAdviceMethod, pointcut,
                aspectInstanceFactory);

        AspectJPointcutAdvisor aspectJPointcutAdvisor = new AspectJPointcutAdvisor(beforeAdvice);
        aspectJPointcutAdvisor.setOrder(order);
        return aspectJPointcutAdvisor;
    }
}
