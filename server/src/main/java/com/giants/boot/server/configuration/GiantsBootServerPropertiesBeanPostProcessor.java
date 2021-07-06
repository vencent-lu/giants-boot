package com.giants.boot.server.configuration;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import java.util.ArrayList;
import java.util.List;

/**
 * ConfigurationPropertiesBeans TODO
 * date time: 2021/6/25 14:33
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public class GiantsBootServerPropertiesBeanPostProcessor implements BeanPostProcessor, PriorityOrdered, ApplicationContextAware {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof GiantsBootServerProperties) {
            System.out.println(bean);
        }
        if (bean instanceof GiantsBootCommonProperties) {
            GiantsBootCommonProperties giantsBootCommonProperties = (GiantsBootCommonProperties)bean;
            GiantsBootCommonProperties.FastJsonConfig fastJsonConfig = giantsBootCommonProperties.getFastJsonConfig();
            if (fastJsonConfig == null) {
                fastJsonConfig = new GiantsBootCommonProperties.FastJsonConfig();
                giantsBootCommonProperties.setFastJsonConfig(fastJsonConfig);
            }
            List<String> ignorePropertyNames = fastJsonConfig.getIgnorePropertyNames();
            if (ignorePropertyNames == null) {
                ignorePropertyNames = new ArrayList<>();
                fastJsonConfig.setIgnorePropertyNames(ignorePropertyNames);
            }
            ignorePropertyNames.add("stackTrace");
            ignorePropertyNames.add("suppressed");
            ignorePropertyNames.add("localizedMessage");
        }
        if (bean instanceof MybatisProperties) {
            MybatisProperties mybatisProperties = (MybatisProperties)bean;
            if (ArrayUtils.isEmpty(mybatisProperties.getMapperLocations())) {
                mybatisProperties.setMapperLocations(new String[] {"classpath*:mapper/*Mapper.xml"});
            }
            Configuration configuration = mybatisProperties.getConfiguration();
            if (configuration == null) {
                configuration = new Configuration();
                mybatisProperties.setConfiguration(configuration);
            }
            if (StringUtils.isEmpty(configuration.getDatabaseId())) {
                configuration.setDatabaseId("MySQL");
            }
            configuration.setMapUnderscoreToCamelCase(true);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof GiantsBootServerProperties) {
            System.out.println(bean);
        }
        if (bean instanceof GiantsBootCommonProperties) {
            System.out.println(bean);
        }
        if (bean instanceof MybatisProperties) {
            System.out.println(bean);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
