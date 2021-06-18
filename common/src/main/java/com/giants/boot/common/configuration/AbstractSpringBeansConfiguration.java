package com.giants.boot.common.configuration;

import com.giants.web.springmvc.advice.JsonResultResponseAdvice;
import com.google.common.collect.Lists;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.Resource;

/**
 * SpringBeansConfiguration TODO
 * date time: 2021/6/8 15:24
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public abstract class AbstractSpringBeansConfiguration {

    @Resource
    private HttpMessageConverter fastJsonHttpMessageConverter;

    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        JsonResultResponseAdvice jsonResultResponseAdvice = new JsonResultResponseAdvice();
        jsonResultResponseAdvice.setJsonpQueryParamName("callback");
        jsonResultResponseAdvice.setUriExcludeList(Lists.newArrayList("/v2/api-docs",
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security","/swagger-resources"));
        requestMappingHandlerAdapter.setResponseBodyAdvice(Lists.newArrayList(jsonResultResponseAdvice));
        requestMappingHandlerAdapter.setMessageConverters(Lists.newArrayList(this.fastJsonHttpMessageConverter));
        return requestMappingHandlerAdapter;
    }

}
