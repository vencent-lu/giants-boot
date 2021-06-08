package com.giants.boot.gateway.configuration;

import com.giants.boot.gateway.configuration.feign.BeanQueryMapNestEncoder;
import com.giants.boot.gateway.configuration.feign.FeignErrorDecoder;
import feign.Feign;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * FeignConfiguration TODO
 * date time: 2021/6/4 14:12
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootGatewayFeignConfiguration {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .queryMapEncoder(new BeanQueryMapNestEncoder())
                .retryer(Retryer.NEVER_RETRY);
    }

    @Bean
    public ErrorDecoder createErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
