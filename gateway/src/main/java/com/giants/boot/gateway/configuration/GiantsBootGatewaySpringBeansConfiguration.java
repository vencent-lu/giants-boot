package com.giants.boot.gateway.configuration;

import com.giants.cache.core.GiantsCache;
import com.giants.cache.core.GiantsCacheManager;
import com.giants.cache.core.aop.GiantsCacheAop;
import com.giants.cache.core.filter.GiantsCacheFilter;
import com.giants.web.filter.WebFilter;
import com.giants.web.springmvc.aop.ControllerValidationAop;
import com.giants.web.springmvc.resolver.JsonResultExceptionResolver;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
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

    @Bean
    public FilterRegistrationBean<WebFilter> createWebFilter() {
        FilterRegistrationBean<WebFilter> webFilter = new FilterRegistrationBean<WebFilter>();
        webFilter.setName("webFilter");
        webFilter.setFilter(new WebFilter());
        webFilter.setUrlPatterns(Lists.newArrayList("*"));
        webFilter.setOrder(Ordered.HIGHEST_PRECEDENCE+2);
        return webFilter;
    }

    @Bean
    public JsonResultExceptionResolver createJsonResultExceptionResolver(HttpMessageConverter<Object> fastJsonHttpMessageConverter) {
        JsonResultExceptionResolver jsonResultExceptionResolver = new JsonResultExceptionResolver();
        //jsonResultExceptionResolver.setIncludeModelAndView(true);
        jsonResultExceptionResolver.setJsonpQueryParamName("callback");
        jsonResultExceptionResolver.setMessageConverters(Lists.newArrayList(fastJsonHttpMessageConverter));
        return jsonResultExceptionResolver;
    }

    @Bean("controllerValidationAop")
    public ControllerValidationAop createControllerValidationAop(ResourcePatternResolver resourcePatternResolver) throws IOException {
        DefaultValidatorFactory validatorFactory = new DefaultValidatorFactory();
        validatorFactory.setValidationConfigLocations((org.springframework.core.io.Resource[]) ArrayUtils.addAll(
                resourcePatternResolver.getResources("classpath*:/validator-rules.xml"),
                resourcePatternResolver.getResources("classpath*:/validation-*.xml")));

        DefaultBeanValidator validator = new DefaultBeanValidator();
        validator.setUseFullyQualifiedClassName(false);
        validator.setValidatorFactory(validatorFactory);

        ControllerValidationAop controllerValidationAop = new ControllerValidationAop();
        controllerValidationAop.setValidator(validator);
        controllerValidationAop.setErrorMessageKey("errors.validation.failure");
        controllerValidationAop.setDontThrowExceptionsReturnTypes(Lists.newArrayList("org.springframework.web.servlet.ModelAndView"));
        return controllerValidationAop;
    }

    @Bean("giantsCacheApiAop")
    public GiantsCacheAop createGiantsCacheApiAop(GiantsCache giantsCache) {
        GiantsCacheAop giantsCacheDaoAop = new GiantsCacheAop();
        giantsCacheDaoAop.setCacheModelName("api");
        giantsCacheDaoAop.setCacheConfigFilePath(giantsCache.getCacheConfigFilePath());
        return giantsCacheDaoAop;
    }

    @Bean
    public FilterRegistrationBean<GiantsCacheFilter> createGiantsCacheFilter(GiantsCache giantsCache,
                                                                             GiantsCacheManager giantsCacheManager) {
        FilterRegistrationBean<GiantsCacheFilter> webFilter = new FilterRegistrationBean<GiantsCacheFilter>();
        webFilter.setName("giantsCacheFilter");
        GiantsCacheFilter giantsCacheFilter = new GiantsCacheFilter();
        giantsCacheFilter.setCacheModelName("servlet");
        giantsCacheFilter.setCacheConfigFilePath(giantsCache.getCacheConfigFilePath());
        webFilter.setFilter(giantsCacheFilter);
        webFilter.setUrlPatterns(Lists.newArrayList("*"));
        webFilter.setOrder(Ordered.HIGHEST_PRECEDENCE+1);
        return webFilter;
    }

}
