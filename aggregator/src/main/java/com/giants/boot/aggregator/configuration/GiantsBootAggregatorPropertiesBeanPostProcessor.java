package com.giants.boot.aggregator.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * GiantsBootAggregatorPropertiesBeanPostProcessor TODO
 * date time: 2024/11/29 15:30
 * Copyright 2024 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.5.0
 */
public class GiantsBootAggregatorPropertiesBeanPostProcessor implements BeanPostProcessor, PriorityOrdered, ApplicationContextAware {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof GiantsBootCommonProperties) {
            GiantsBootCommonProperties giantsBootCommonProperties = (GiantsBootCommonProperties)bean;
            GiantsBootCommonProperties.FastJsonConfig fastJsonConfig = giantsBootCommonProperties.getFastJsonConfig();
            if (fastJsonConfig == null) {
                fastJsonConfig = new GiantsBootCommonProperties.FastJsonConfig();
                giantsBootCommonProperties.setFastJsonConfig(fastJsonConfig);
            }
            SerializerFeature[] serializerFeatures = fastJsonConfig.getSerializerFeatures();

            if (serializerFeatures == null) {
                fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.DisableCircularReferenceDetect});
            } else {
                fastJsonConfig.setSerializerFeatures((SerializerFeature[])ArrayUtils.add(serializerFeatures,
                        serializerFeatures.length, SerializerFeature.DisableCircularReferenceDetect));
            }
        }
        if (bean instanceof GiantsBootAggregatorProperties) {
            GiantsBootAggregatorProperties giantsBootAggregatorProperties = (GiantsBootAggregatorProperties)bean;
            GiantsBootAggregatorProperties.JsonResultResponseConfig jsonResultResponseConfig =
                    giantsBootAggregatorProperties.getJsonResultResponseConfig();
            if (jsonResultResponseConfig.getJsonpQueryParamNameList() == null) {
                jsonResultResponseConfig.setJsonpQueryParamNameList(new ArrayList<>());
            }
            jsonResultResponseConfig.getJsonpQueryParamNameList().add("callback");
            if (jsonResultResponseConfig.getUriExcludeList() == null) {
                jsonResultResponseConfig.setUriExcludeList(new ArrayList<>());
            }
            jsonResultResponseConfig.getUriExcludeList().add("/v2/api-docs");
            jsonResultResponseConfig.getUriExcludeList().add("/swagger-resources/configuration/ui");
            jsonResultResponseConfig.getUriExcludeList().add("/swagger-resources/configuration/security");
            jsonResultResponseConfig.getUriExcludeList().add("/swagger-resources");

            GiantsBootAggregatorProperties.SwaggerConfig swaggerConfig = giantsBootAggregatorProperties.getSwaggerConfig();
            if (swaggerConfig == null) {
                swaggerConfig = new GiantsBootAggregatorProperties.SwaggerConfig();
                giantsBootAggregatorProperties.setSwaggerConfig(swaggerConfig);
            }
            List<Class> ignoreParameterTypes = swaggerConfig.getIgnoreParameterTypes();
            if (ignoreParameterTypes == null) {
                ignoreParameterTypes = new ArrayList<>();
                swaggerConfig.setIgnoreParameterTypes(ignoreParameterTypes);
            }
            ignoreParameterTypes.add(HttpSession.class);
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 2;
    }
}
