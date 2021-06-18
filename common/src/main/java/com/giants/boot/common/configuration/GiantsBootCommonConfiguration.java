package com.giants.boot.common.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * GiantsBootCommonConfiguration TODO
 * date time: 2021/6/4 15:43
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(GiantsBootCommonProperties.class)
@Import({
        GiantsBootCommonSpringBeansConfiguration.class,
        GiantsBootCommonFeignConfiguration.class
})
public class GiantsBootCommonConfiguration {
}
