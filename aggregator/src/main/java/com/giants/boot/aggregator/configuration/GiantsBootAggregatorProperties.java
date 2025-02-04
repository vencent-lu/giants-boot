package com.giants.boot.aggregator.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * GiantsBootAggregatorProperties TODO
 * date time: 2024/11/29 15:30
 * Copyright 2024 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.5.0
 */
@ConfigurationProperties(prefix = "giants.boot")
public class GiantsBootAggregatorProperties {

    /**
     * JsonResult封装response返回结果配置
     */
    private JsonResultResponseConfig jsonResultResponseConfig;

    /**
     * swagger配置
     */
    private SwaggerConfig swaggerConfig;

    public JsonResultResponseConfig getJsonResultResponseConfig() {
        if (this.jsonResultResponseConfig == null) {
            this.jsonResultResponseConfig = new JsonResultResponseConfig();
        }
        return this.jsonResultResponseConfig;
    }

    public void setJsonResultResponseConfig(JsonResultResponseConfig jsonResultResponseConfig) {
        this.jsonResultResponseConfig = jsonResultResponseConfig;
    }

    public SwaggerConfig getSwaggerConfig() {
        if (this.swaggerConfig == null) {
            this.swaggerConfig = new SwaggerConfig();
        }
        return this.swaggerConfig;
    }

    public void setSwaggerConfig(SwaggerConfig swaggerConfig) {
        this.swaggerConfig = swaggerConfig;
    }

    public static class JsonResultResponseConfig {
        /**
         * Jsonp 回调函数名列表
         */
        private List<String> jsonpQueryParamNameList;
        /**
         * 不需要JsonResult封装的请求uri
         */
        private List<String> uriExcludeList;

        public List<String> getJsonpQueryParamNameList() {
            return jsonpQueryParamNameList;
        }

        public void setJsonpQueryParamNameList(List<String> jsonpQueryParamNameList) {
            this.jsonpQueryParamNameList = jsonpQueryParamNameList;
        }

        public List<String> getUriExcludeList() {
            return uriExcludeList;
        }

        public void setUriExcludeList(List<String> uriExcludeList) {
            this.uriExcludeList = uriExcludeList;
        }
    }

    public static class SwaggerConfig {
        /**
         * API文档信息配置，名称、作者、版本等配置信息
         */
        private ApiInfo apiInfo;
        /**
         * 是否开启Swagger文档
         */
        private boolean enabled = false;
        /**
         * 使用默认Response Messages 除200之外的状态
         */
        private boolean useDefaultResponseMessages = false;
        /**
         * 只展示Api注解的方法接口
         */
        private boolean onlyApiAnnotationClass = true;
        /**
         * 忽略参数类型，入参 和 返回结果对象都会忽略
         */
        private List<Class> ignoreParameterTypes;

        /**
         * 忽略请求参数类型，入参对象都会忽略
         */
        private List<Class> ignoreRequestParameterTypes;

        public ApiInfo getApiInfo() {
            return apiInfo;
        }

        public void setApiInfo(ApiInfo apiInfo) {
            this.apiInfo = apiInfo;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isUseDefaultResponseMessages() {
            return useDefaultResponseMessages;
        }

        public void setUseDefaultResponseMessages(boolean useDefaultResponseMessages) {
            this.useDefaultResponseMessages = useDefaultResponseMessages;
        }

        public boolean isOnlyApiAnnotationClass() {
            return onlyApiAnnotationClass;
        }

        public void setOnlyApiAnnotationClass(boolean onlyApiAnnotationClass) {
            this.onlyApiAnnotationClass = onlyApiAnnotationClass;
        }

        public List<Class> getIgnoreParameterTypes() {
            return ignoreParameterTypes;
        }

        public void setIgnoreParameterTypes(List<Class> ignoreParameterTypes) {
            this.ignoreParameterTypes = ignoreParameterTypes;
        }

        public List<Class> getIgnoreRequestParameterTypes() {
            return ignoreRequestParameterTypes;
        }

        public void setIgnoreRequestParameterTypes(List<Class> ignoreRequestParameterTypes) {
            this.ignoreRequestParameterTypes = ignoreRequestParameterTypes;
        }

        public static class ApiInfo {
            /**
             * 文档标题
             */
            private String title;
            /**
             * 文档说明
             */
            private String description;
            /**
             * 文档版本
             */
            private String version;
            /**
             * 团队服务链接
             */
            private String termsOfServiceUrl;
            /**
             * 联系人信息
             */
            private Contact contact;
            /**
             * 许可信息 如：Apache 2.0
             */
            private String license;
            /**
             * 许可网站地址
             */
            private String licenseUrl;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getTermsOfServiceUrl() {
                return termsOfServiceUrl;
            }

            public void setTermsOfServiceUrl(String termsOfServiceUrl) {
                this.termsOfServiceUrl = termsOfServiceUrl;
            }

            public Contact getContact() {
                return contact;
            }

            public void setContact(Contact contact) {
                this.contact = contact;
            }

            public String getLicense() {
                return license;
            }

            public void setLicense(String license) {
                this.license = license;
            }

            public String getLicenseUrl() {
                return licenseUrl;
            }

            public void setLicenseUrl(String licenseUrl) {
                this.licenseUrl = licenseUrl;
            }

            public static class Contact {
                private String name;
                private String url;
                private String email;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }
            }
        }
    }
}
