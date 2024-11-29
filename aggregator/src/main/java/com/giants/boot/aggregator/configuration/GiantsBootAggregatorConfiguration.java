package com.giants.boot.aggregator.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * GiantsBootAggregatorConfiguration TODO
 * date time: 2024/11/29 15:30
 * Copyright 2024 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.5.0
 */
@Configuration
@EnableConfigurationProperties(GiantsBootAggregatorProperties.class)
@EnableEurekaClient
@EnableFeignClients(basePackages = "${giants.boot.base-package:com.giants}.**.api")
@ComponentScan(basePackages = {"${giants.boot.base-package:com.giants}.**.controller", "${giants.boot.base-package:com.giants}.**.configuration"})
@Import({
        GiantsBootAggregatorSpringBeansConfiguration.class,
        GiantsBootAggregatorAopConfiguration.class,
        GiantsSwaggerConfig.class
})
public class GiantsBootAggregatorConfiguration {
}
