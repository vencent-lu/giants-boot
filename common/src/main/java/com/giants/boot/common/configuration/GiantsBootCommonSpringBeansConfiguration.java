package com.giants.boot.common.configuration;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.giants.analyse.aop.EnterExecutionTimeProfilerAop;
import com.giants.analyse.filter.ExecutionTimeProfilerFilter;
import com.giants.cache.core.GiantsCache;
import com.giants.cache.core.GiantsCacheManager;
import com.giants.cache.nocaching.impl.NoCachingImpl;
import com.giants.cache.redis.RedisClient;
import com.giants.cache.redis.SpringDataRedisClient;
import com.giants.cache.redis.impl.GiantsRedisImpl;
import com.giants.common.SpringContextHelper;
import com.giants.common.collections.CollectionUtils;
import com.giants.common.fastjson.FastJson;
import com.giants.web.springmvc.json.JsonSerializePropertyFilter;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import java.io.IOException;

/**
 * SpringBeansConfiguration TODO
 * date time: 2021/6/4 15:31
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootCommonSpringBeansConfiguration {

    @Bean
    public SpringContextHelper createSpringContextHelper() {
        return new SpringContextHelper();
    }

    @Bean
    public ResourcePatternResolver createResourcePatternResolver() {
        return new PathMatchingResourcePatternResolver();
    }

    @Bean
    public FilterRegistrationBean<ExecutionTimeProfilerFilter> createExecutionTimeProfilerFilter(
            GiantsBootCommonProperties giantsBootCommonProperties) {
        GiantsBootCommonProperties.CallStackTimeAnalyseLogConfig callStackTimeAnalyseLogConfig =
                giantsBootCommonProperties.getCallStackTimeAnalyseLogConfig();
        FilterRegistrationBean<ExecutionTimeProfilerFilter> executionTimeProfilerFilter =
                new FilterRegistrationBean<ExecutionTimeProfilerFilter>();
        executionTimeProfilerFilter.setName("timer");
        ExecutionTimeProfilerFilter filter = new ExecutionTimeProfilerFilter();
        if (callStackTimeAnalyseLogConfig != null) {
            filter.setLogCallStackTimeAnalyse(true);
            if (callStackTimeAnalyseLogConfig.getHttpRequestExeTimeThreshold() != null) {
                filter.setThreshold(callStackTimeAnalyseLogConfig.getHttpRequestExeTimeThreshold());
            }
        }
        executionTimeProfilerFilter.setFilter(filter);
        executionTimeProfilerFilter.setUrlPatterns(Lists.newArrayList("*"));
        executionTimeProfilerFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return executionTimeProfilerFilter;
    }

    @Bean("messageSource")
    public ResourceBundleMessageSource createResourceBundleMessageSource(ResourcePatternResolver resourcePatternResolver) throws IOException {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        org.springframework.core.io.Resource[] resources = resourcePatternResolver.getResources("classpath*:/*-resources.properties");
        if (ArrayUtils.isNotEmpty(resources)) {
            String[] sourceNames = new String[resources.length];
            for (int i=0; i<resources.length; i++) {
                sourceNames[i] = resources[i].getFilename().replaceAll("\\.properties", "");
            }
            messageSource.setBasenames(sourceNames);
        }
        return messageSource;
    }

    @Bean
    public FastJsonConfig createFastJsonConfig(GiantsBootCommonProperties giantsBootCommonProperties) {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        /**
         * 如果需要添加自定义异常反序列化 在 resources目录下新建 fastjson.properties 文件
         * 并添加 fastjson.parser.autoTypeAccept=com.user-defined.exception.
         */
        fastJsonConfig.getParserConfig().addAccept("com.giants.common.exception.");

        if (giantsBootCommonProperties.getFastJsonConfig() != null) {
            if (StringUtils.isNotEmpty(giantsBootCommonProperties.getFastJsonConfig().getDateFormat())) {
                fastJsonConfig.setDateFormat(giantsBootCommonProperties.getFastJsonConfig().getDateFormat());
            }
            if (giantsBootCommonProperties.getFastJsonConfig().getSerializerFeatures() != null) {
                fastJsonConfig.setSerializerFeatures(giantsBootCommonProperties.getFastJsonConfig().getSerializerFeatures());
            }
            if (CollectionUtils.isNotEmpty(giantsBootCommonProperties.getFastJsonConfig().getIgnorePropertyNames())) {
                JsonSerializePropertyFilter jsonSerializePropertyFilter = new JsonSerializePropertyFilter();
                jsonSerializePropertyFilter.setIgnorePropertyNames(giantsBootCommonProperties.getFastJsonConfig().getIgnorePropertyNames());
                fastJsonConfig.setSerializeFilters(jsonSerializePropertyFilter);
            }
        }
        return fastJsonConfig;
    }

    @Bean
    public FastJson createFastJson(FastJsonConfig fastJsonConfig) {
        FastJson fastJson = new FastJson();
        fastJson.setFastJsonConfig(fastJsonConfig);
        return fastJson;
    }

    @Bean("fastJsonHttpMessageConverter")
    public HttpMessageConverter<Object> createHttpMessageConverter(FastJsonConfig fastJsonConfig) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }

    @Bean("enterExecutionTimeProfilerAop")
    public EnterExecutionTimeProfilerAop createEnterExecutionTimeProfilerAop(GiantsBootCommonProperties giantsBootCommonProperties) {
        GiantsBootCommonProperties.CallStackTimeAnalyseLogConfig callStackTimeAnalyseLogConfig =
                giantsBootCommonProperties.getCallStackTimeAnalyseLogConfig();
        EnterExecutionTimeProfilerAop enterExecutionTimeProfilerAop = new EnterExecutionTimeProfilerAop();
        if (callStackTimeAnalyseLogConfig != null) {
            enterExecutionTimeProfilerAop.setLogCallStackTimeAnalyse(true);
            enterExecutionTimeProfilerAop.setShowArguments(callStackTimeAnalyseLogConfig.isShowArguments());
            if (callStackTimeAnalyseLogConfig.getMethodExeTimeThreshold() != null) {
                enterExecutionTimeProfilerAop.setThreshold(callStackTimeAnalyseLogConfig.getMethodExeTimeThreshold());
            }
        }
        return enterExecutionTimeProfilerAop;
    }

    private RedisClient createRedisClient(GiantsBootCommonProperties.CacheConfig.Redis redis) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        if (StringUtils.isNotEmpty(redis.getHostName())) {
            redisStandaloneConfiguration.setHostName(redis.getHostName());
        }
        if (redis.getPort() != null) {
            redisStandaloneConfiguration.setPort(redis.getPort());
        }
        if (redis.getDatabase() != null) {
            redisStandaloneConfiguration.setDatabase(redis.getDatabase());
        }
        if (StringUtils.isNotEmpty(redis.getUsername())) {
            redisStandaloneConfiguration.setUsername(redis.getUsername());
        }
        if (StringUtils.isNotEmpty(redis.getPassword())) {
            redisStandaloneConfiguration.setPassword(redis.getPassword());
        }
        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder =
                LettuceClientConfiguration.builder();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration
                , lettuceClientConfigurationBuilder.build());
        lettuceConnectionFactory.afterPropertiesSet();
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.afterPropertiesSet();

        SpringDataRedisClient giantsRedisClient = new SpringDataRedisClient();
        giantsRedisClient.setRedisTemplate(redisTemplate);
        return giantsRedisClient;
    }

    @Bean
    public GiantsCache createGiantsCache(GiantsBootCommonProperties giantsBootCommonProperties, RedisTemplate redisTemplate) {
        if (giantsBootCommonProperties.getCacheConfig() == null) {
            return new NoCachingImpl();
        }
        GiantsBootCommonProperties.CacheConfig cacheConfig = giantsBootCommonProperties.getCacheConfig();
        if (cacheConfig.getCacheType() == null) {
            return new NoCachingImpl();
        }
        switch (cacheConfig.getCacheType()) {
            case REDIS:
                GiantsRedisImpl giantsRedisImpl = new GiantsRedisImpl();
                if (cacheConfig.getRedis() != null) {
                    giantsRedisImpl.setRedisClient(this.createRedisClient(cacheConfig.getRedis()));
                } else {
                    SpringDataRedisClient giantsRedisClient = new SpringDataRedisClient();
                    giantsRedisClient.setRedisTemplate(redisTemplate);
                    giantsRedisImpl.setRedisClient(giantsRedisClient);
                }
                return giantsRedisImpl;
            case MEMCACHED:
            case EHCACHE:
            default:
                return new NoCachingImpl();
        }
    }

    @Bean
    public GiantsCacheManager createGiantsCacheManager(GiantsCache giantsCache) throws XmlMapException {
        return new GiantsCacheManager(giantsCache);
    }
}
