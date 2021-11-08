package com.giants.boot.gateway.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * ConfigurationPropertiesBeans TODO
 * date time: 2021/6/25 14:33
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
public class GiantsBootGatewayPropertiesBeanPostProcessor implements BeanPostProcessor, PriorityOrdered, ApplicationContextAware {

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
        if (bean instanceof GiantsBootGatewayProperties) {
            GiantsBootGatewayProperties giantsBootGatewayProperties = (GiantsBootGatewayProperties)bean;
            GiantsBootGatewayProperties.JsonResultResponseConfig jsonResultResponseConfig =
                    giantsBootGatewayProperties.getJsonResultResponseConfig();
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

            GiantsBootGatewayProperties.SwaggerConfig swaggerConfig = giantsBootGatewayProperties.getSwaggerConfig();
            if (swaggerConfig == null) {
                swaggerConfig = new GiantsBootGatewayProperties.SwaggerConfig();
                giantsBootGatewayProperties.setSwaggerConfig(swaggerConfig);
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
