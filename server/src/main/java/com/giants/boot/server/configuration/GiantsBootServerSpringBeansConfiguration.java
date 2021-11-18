package com.giants.boot.server.configuration;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.cache.core.GiantsCache;
import com.giants.cache.core.aop.GiantsCacheAop;
import com.giants.cache.redis.RedisClient;
import com.giants.cache.redis.SpringDataRedisClient;
import com.giants.web.springmvc.resolver.JsonExceptionResolver;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * GiantsBootServerSpringBeansConfiguration TODO
 * date time: 2021/6/15 18:29
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootServerSpringBeansConfiguration {

    @Bean
    public GiantsBootServerPropertiesBeanPostProcessor createGiantsBootServerPropertiesBeanPostProcessor() {
        return new GiantsBootServerPropertiesBeanPostProcessor();
    }

    @Bean
    public JsonExceptionResolver createJsonExceptionResolver(HttpMessageConverter<Object> fastJsonHttpMessageConverter,
                                                             GiantsBootCommonProperties giantsBootCommonProperties) {
        JsonExceptionResolver jsonExceptionResolver = new JsonExceptionResolver();
        jsonExceptionResolver.setMessageConverters(Lists.newArrayList(fastJsonHttpMessageConverter));
        if (giantsBootCommonProperties.getFeignConfig() != null
                && giantsBootCommonProperties.getFeignConfig().getResponseExceptionStatus() != null) {
            jsonExceptionResolver.setResponseExceptionStatus(giantsBootCommonProperties.getFeignConfig().getResponseExceptionStatus());
        }
        return jsonExceptionResolver;
    }

    @Bean("redisClient")
    public RedisClient createRedisClient(RedisTemplate redisTemplate) {
        SpringDataRedisClient redisClient = new SpringDataRedisClient();
        redisTemplate.setEnableDefaultSerializer(false);
        redisClient.setRedisTemplate(redisTemplate);
        return redisClient;
    }

    @Bean("giantsCacheServiceAop")
    public GiantsCacheAop createGiantsCacheServiceAop(GiantsCache giantsCache) {
        GiantsCacheAop giantsCacheServiceAop = new GiantsCacheAop();
        giantsCacheServiceAop.setCacheModelName("service");
        giantsCacheServiceAop.setCacheConfigFilePath(giantsCache.getCacheConfigFilePath());
        return giantsCacheServiceAop;
    }

    @Bean("giantsCacheDaoAop")
    public GiantsCacheAop createGiantsCacheDaoAop(GiantsCache giantsCache) {
        GiantsCacheAop giantsCacheDaoAop = new GiantsCacheAop();
        giantsCacheDaoAop.setCacheModelName("dao");
        giantsCacheDaoAop.setCacheConfigFilePath(giantsCache.getCacheConfigFilePath());
        return giantsCacheDaoAop;
    }

}
