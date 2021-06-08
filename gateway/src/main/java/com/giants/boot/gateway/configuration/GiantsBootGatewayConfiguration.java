package com.giants.boot.gateway.configuration;

import com.giants.boot.gateway.configuration.annotation.AnnotationAspect;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * GiantsBootGatewayConfiguration TODO
 * date time: 2021/6/5 11:09
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(GiantsBootGatewayProperties.class)
@EnableEurekaClient
//@EnableFeignClients(basePackages = "{basePackage}.**.api")
//@ComponentScan(basePackages = {"{basePackage}.**.controller", "{basePackage}.**.configuration"})
@Import({
        GiantsBootGatewaySpringBeansConfiguration.class,
        GiantsBootGatewayFeignConfiguration.class,
        GiantsBootGatewayAopConfiguration.class,
        SwaggerConfig.class,
        AnnotationAspect.class
})
public class GiantsBootGatewayConfiguration {
}
