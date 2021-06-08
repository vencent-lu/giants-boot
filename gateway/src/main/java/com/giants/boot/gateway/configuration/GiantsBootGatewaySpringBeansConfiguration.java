package com.giants.boot.gateway.configuration;

import com.giants.web.filter.WebFilter;
import com.giants.web.springmvc.advice.JsonResultResponseAdvice;
import com.giants.web.springmvc.aop.ControllerValidationAop;
import com.giants.web.springmvc.resolver.JsonResultExceptionResolver;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.springmodules.validation.commons.DefaultValidatorFactory;

import java.io.IOException;

/**
 * SpringBeansConfiguration TODO
 * date time: 2021/6/5 10:50
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
public class GiantsBootGatewaySpringBeansConfiguration {

    @javax.annotation.Resource
    private HttpMessageConverter fastJsonHttpMessageConverter;

    @Bean
    public FilterRegistrationBean createWebFilter() {
        FilterRegistrationBean webFilter = new FilterRegistrationBean();
        webFilter.setName("webFilter");
        webFilter.setFilter(new WebFilter());
        webFilter.setUrlPatterns(Lists.newArrayList("*"));
        return webFilter;
    }

    @Bean
    public RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        JsonResultResponseAdvice jsonResultResponseAdvice = new JsonResultResponseAdvice();
        jsonResultResponseAdvice.setJsonpQueryParamName("callback");
        jsonResultResponseAdvice.setUriExcludeList(Lists.newArrayList("/v2/api-docs",
                "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security","/swagger-resources"));
        requestMappingHandlerAdapter.setResponseBodyAdvice(Lists.newArrayList(jsonResultResponseAdvice));
        requestMappingHandlerAdapter.setMessageConverters(Lists.newArrayList(this.fastJsonHttpMessageConverter));
        return requestMappingHandlerAdapter;
    }

    @Bean
    public JsonResultExceptionResolver createJsonResultExceptionResolver() {
        JsonResultExceptionResolver jsonResultExceptionResolver = new JsonResultExceptionResolver();
        //jsonResultExceptionResolver.setIncludeModelAndView(true);
        jsonResultExceptionResolver.setJsonpQueryParamName("callback");
        jsonResultExceptionResolver.setMessageConverters(Lists.newArrayList(this.fastJsonHttpMessageConverter));
        return jsonResultExceptionResolver;
    }

    @Bean("controllerValidationAop")
    public ControllerValidationAop createControllerValidationAop() throws IOException {
        DefaultValidatorFactory validatorFactory = new DefaultValidatorFactory();
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
                new PathMatchingResourcePatternResolver();
        validatorFactory.setValidationConfigLocations((Resource[]) ArrayUtils.addAll(
                pathMatchingResourcePatternResolver.getResources("classpath*:/validator-rules.xml"),
                pathMatchingResourcePatternResolver.getResources("classpath*:/validation-*.xml")));

        DefaultBeanValidator validator = new DefaultBeanValidator();
        validator.setUseFullyQualifiedClassName(false);
        validator.setValidatorFactory(validatorFactory);

        ControllerValidationAop controllerValidationAop = new ControllerValidationAop();
        controllerValidationAop.setValidator(validator);
        controllerValidationAop.setErrorMessageKey("errors.validation.failure");
        controllerValidationAop.setDontThrowExceptionsReturnTypes(Lists.newArrayList("org.springframework.web.servlet.ModelAndView"));
        return controllerValidationAop;
    }

}
