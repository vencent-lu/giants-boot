package com.giants.boot.common.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.giants.common.SpringContextHelper;
import com.giants.common.collections.CollectionUtils;
import com.giants.web.springmvc.json.JsonSerializePropertyFilter;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;

import javax.annotation.Resource;

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

    @Resource
    private GiantsBootCommonProperties giantsBootCommonProperties;

    @Bean
    public SpringContextHelper createSpringContextHelper() {
        return new SpringContextHelper();
    }

    @Bean("messageSource")
    public ResourceBundleMessageSource createResourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        if (this.giantsBootCommonProperties.getMessageSourceNames() != null) {
            messageSource.setBasenames(this.giantsBootCommonProperties.getMessageSourceNames());
        }
        return messageSource;
    }

    @Bean("fastJsonHttpMessageConverter")
    public HttpMessageConverter createHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON));
        if (this.giantsBootCommonProperties.getFastJsonConfig() != null) {
            FastJsonConfig fastJsonConfig = new FastJsonConfig();
            fastJsonConfig.setDateFormat(this.giantsBootCommonProperties.getFastJsonConfig().getDateFormat());
            fastJsonConfig.setSerializerFeatures(this.giantsBootCommonProperties.getFastJsonConfig().getSerializerFeatures());
            JsonSerializePropertyFilter jsonSerializePropertyFilter = new JsonSerializePropertyFilter();
            jsonSerializePropertyFilter.setIgnorePropertyNames(this.giantsBootCommonProperties.getFastJsonConfig().getIgnorePropertyNames());
            fastJsonConfig.setSerializeFilters(jsonSerializePropertyFilter);
            fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        }
        return fastJsonHttpMessageConverter;
    }
}
