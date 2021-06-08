package com.giants.boot.gateway.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * GiantsBootGatewayProperties TODO
 * date time: 2021/6/6 22:24
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@ConfigurationProperties(prefix = "giants.boot.gateway")
public class GiantsBootGatewayProperties {

    private String swaggerTest;

    public String getSwaggerTest() {
        return swaggerTest;
    }

    public void setSwaggerTest(String swaggerTest) {
        this.swaggerTest = swaggerTest;
    }

    private SwaggerConfig swaggerConfig;

    public SwaggerConfig getSwaggerConfig() {
        return swaggerConfig;
    }

    public void setSwaggerConfig(SwaggerConfig swaggerConfig) {
        this.swaggerConfig = swaggerConfig;
    }

    public static class SwaggerConfig {
        private ApiInfo apiInfo;
        private boolean enabled = false;
        private boolean useDefaultResponseMessages = false;
        private boolean onlyApiAnnotationClass = true;

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

        public static class ApiInfo {
            private String title;
            private String description;
            private String version;
            private String termsOfServiceUrl;
            private Contact contact;
            private String license;
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
