package com.giants.boot.server.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * GiantsBootServerConfiguration TODO
 * date time: 2021/6/15 19:59
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(GiantsBootServerProperties.class)
@EnableEurekaClient
@MapperScan("${giants.boot.base-package:com.giants}.**.dao")
@ComponentScan(basePackages = {"${giants.boot.base-package:com.giants}.**.service.impl", "${giants.boot.base-package:com.giants}.**.configuration"})
@Import({
        GiantsBootServerSpringBeansConfiguration.class,
        GiantsBootServerAopConfiguration.class,
        GiantsBootServerTransactionConfiguration.class
})
public class GiantsBootServerConfiguration {
}
