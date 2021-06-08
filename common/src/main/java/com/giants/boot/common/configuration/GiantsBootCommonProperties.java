package com.giants.boot.common.configuration;

import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * GiantsBootCommonProperties TODO
 * date time: 2021/6/4 15:35
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.0
 */
@ConfigurationProperties(prefix = "giants.boot.common")
public class GiantsBootCommonProperties {

    private String basePackage;

    private String[] messageSourceNames;

    private FastJsonConfig fastJsonConfig;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String[] getMessageSourceNames() {
        return messageSourceNames;
    }

    public void setMessageSourceNames(String[] messageSourceNames) {
        this.messageSourceNames = messageSourceNames;
    }

    public FastJsonConfig getFastJsonConfig() {
        return fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public static class FastJsonConfig {

        private String dateFormat;

        private SerializerFeature[] serializerFeatures;

        private List<String> ignorePropertyNames;

        public String getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }

        public SerializerFeature[] getSerializerFeatures() {
            return serializerFeatures;
        }

        public void setSerializerFeatures(SerializerFeature[] serializerFeatures) {
            this.serializerFeatures = serializerFeatures;
        }

        public List<String> getIgnorePropertyNames() {
            return ignorePropertyNames;
        }

        public void setIgnorePropertyNames(List<String> ignorePropertyNames) {
            this.ignorePropertyNames = ignorePropertyNames;
        }
    }
}
