package com.giants.boot.gateway.configuration;

import com.giants.boot.common.configuration.GiantsBootCommonProperties;
import com.giants.common.collections.CollectionUtils;
import com.giants.swagger.configuration.GiantsSwaggerProperties;
import com.giants.swagger.configuration.ReturnResultClass;
import com.giants.web.springmvc.json.JsonResult;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * SwaggerConfig TODO
 * date time: 2021/5/10 11:05
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@Configuration
@EnableSwagger2
public class GiantsSwaggerConfig {

    @Resource
    private GiantsBootCommonProperties giantsBootCommonProperties;
    @Resource
    private GiantsBootGatewayProperties giantsBootGatewayProperties;
    @Resource
    private GiantsSwaggerProperties giantsSwaggerProperties;

    @Bean
    public Docket docker(){
        if (this.giantsBootCommonProperties.getFastJsonConfig() != null
                && CollectionUtils.isEmpty(this.giantsSwaggerProperties.getIgnoreModelPropertyNames())) {
            this.giantsSwaggerProperties.setIgnoreModelPropertyNames(this.giantsBootCommonProperties.getFastJsonConfig().getIgnorePropertyNames());
        }
        GiantsBootGatewayProperties.SwaggerConfig swaggerConfig = this.giantsBootGatewayProperties.getSwaggerConfig();
        if (CollectionUtils.isNotEmpty(swaggerConfig.getIgnoreParameterTypes())
                && CollectionUtils.isEmpty(this.giantsSwaggerProperties.getIgnoreParameterTypes())) {
            this.giantsSwaggerProperties.setIgnoreParameterTypes(swaggerConfig.getIgnoreParameterTypes());
        }
        if (CollectionUtils.isNotEmpty(swaggerConfig.getIgnoreRequestParameterTypes())
                && CollectionUtils.isEmpty(this.giantsSwaggerProperties.getIgnoreRequestParameterTypes())) {
            this.giantsSwaggerProperties.setIgnoreRequestParameterTypes(swaggerConfig.getIgnoreRequestParameterTypes());
        }
        if (this.giantsSwaggerProperties.getReturnResultClass() == null) {
            ReturnResultClass returnResultClass = new ReturnResultClass();
            returnResultClass.setType(JsonResult.class);
            returnResultClass.setDataProperty("data");
            this.giantsSwaggerProperties.setReturnResultClass(returnResultClass);
        }

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerConfig.isEnabled())
                .useDefaultResponseMessages(false);
        if (swaggerConfig.getApiInfo() != null) {
            docket.apiInfo(this.apiInfo(swaggerConfig.getApiInfo()));
        }
        ApiSelectorBuilder apiSelectorBuilder = docket.select();
        if (swaggerConfig.isOnlyApiAnnotationClass()) {
            apiSelectorBuilder.apis(RequestHandlerSelectors.withClassAnnotation(Api.class));
        }
        return apiSelectorBuilder.paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo(GiantsBootGatewayProperties.SwaggerConfig.ApiInfo apiInfo){
        Contact contact = null;
        if (apiInfo.getContact() != null) {
            contact = new Contact(apiInfo.getContact().getName(), apiInfo.getContact().getUrl(),
                    apiInfo.getContact().getEmail());
        }
        return new ApiInfo(
                apiInfo.getTitle(),
                apiInfo.getDescription(),
                apiInfo.getVersion(),
                apiInfo.getTermsOfServiceUrl(),
                contact,
                apiInfo.getLicense(),
                apiInfo.getLicenseUrl(),
                new ArrayList<VendorExtension>());
    }
}
