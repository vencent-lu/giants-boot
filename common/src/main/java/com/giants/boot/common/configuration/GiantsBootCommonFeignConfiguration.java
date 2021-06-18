package com.giants.boot.common.configuration;

import com.giants.feign.codec.FeignErrorDecoder;
import com.giants.feign.querymap.BeanQueryMapNestEncoder;
import feign.Feign;
import feign.QueryMapEncoder;
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
public class GiantsBootCommonFeignConfiguration {

    @Bean
    public Feign.Builder feignBuilder() {
        return Feign.builder()
                .retryer(Retryer.NEVER_RETRY);
    }

    @Bean
    public QueryMapEncoder createQueryMapEncoder() {
        return new BeanQueryMapNestEncoder();
    }

    @Bean
    public ErrorDecoder createErrorDecoder() {
        return new FeignErrorDecoder();
    }
}
