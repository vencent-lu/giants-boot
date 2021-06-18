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
@ConfigurationProperties(prefix = "giants.boot")
public class GiantsBootCommonProperties {
    /**
     * Giants 框架扫描基础包名前缀
     */
    private String basePackage;
    /**
     * FastJson 配置
     */
    private FastJsonConfig fastJsonConfig;
    /**
     * 方法调用栈执行时间分析日志配置
     */
    private CallStackTimeAnalyseLogConfig callStackTimeAnalyseLogConfig;
    /**
     * 缓存配置
     */
    private CacheConfig cacheConfig;


    public String getBasePackage() {
        if (basePackage == null) {
            return "com.giants";
        }
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public FastJsonConfig getFastJsonConfig() {
        return fastJsonConfig;
    }

    public void setFastJsonConfig(FastJsonConfig fastJsonConfig) {
        this.fastJsonConfig = fastJsonConfig;
    }

    public CallStackTimeAnalyseLogConfig getCallStackTimeAnalyseLogConfig() {
        return callStackTimeAnalyseLogConfig;
    }

    public void setCallStackTimeAnalyseLogConfig(CallStackTimeAnalyseLogConfig callStackTimeAnalyseLogConfig) {
        this.callStackTimeAnalyseLogConfig = callStackTimeAnalyseLogConfig;
    }

    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    public static class FastJsonConfig {

        /**
         * 日期时间格式
         */
        private String dateFormat;
        /**
         * 序列化特性配置
         */
        private SerializerFeature[] serializerFeatures;
        /**
         * 忽略属性名称
         */
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

    public static class CallStackTimeAnalyseLogConfig {
        /**
         * http请求执行时间阈值, 超过阈值打印warn日志 单位 ms
         */
        private Integer httpRequestExeTimeThreshold;
        /**
         * 方法执行时间阈值，超过阈值打印warn日志 单位 ms
         */
        private Integer methodExeTimeThreshold;
        /**
         * 日志中是否展示方法参数
         */
        private boolean showArguments = false;

        public Integer getHttpRequestExeTimeThreshold() {
            return httpRequestExeTimeThreshold;
        }

        public void setHttpRequestExeTimeThreshold(Integer httpRequestExeTimeThreshold) {
            this.httpRequestExeTimeThreshold = httpRequestExeTimeThreshold;
        }

        public Integer getMethodExeTimeThreshold() {
            return methodExeTimeThreshold;
        }

        public void setMethodExeTimeThreshold(Integer methodExeTimeThreshold) {
            this.methodExeTimeThreshold = methodExeTimeThreshold;
        }

        public boolean isShowArguments() {
            return showArguments;
        }

        public void setShowArguments(boolean showArguments) {
            this.showArguments = showArguments;
        }
    }

    public static class CacheConfig {
        /**
         * 使用缓存方式：REDIS, MEMCACHED, EHCACHE
         */
        private CacheType cacheType;
        /**
         * redis 连接配置
         */
        private Redis redis;

        public CacheType getCacheType() {
            return cacheType;
        }

        public void setCacheType(CacheType cacheType) {
            this.cacheType = cacheType;
        }

        public Redis getRedis() {
            return redis;
        }

        public void setRedis(Redis redis) {
            this.redis = redis;
        }

        public static class Redis {
            /**
             * redis主机名
             */
            private String hostName;
            /**
             * redis端口
             */
            private Integer port;
            /**
             * redis database
             */
            private Integer database;
            /**
             * 登录用户名
             */
            private String username;
            /**
             * 登录密码
             */
            private String password;

            public String getHostName() {
                return hostName;
            }

            public void setHostName(String hostName) {
                this.hostName = hostName;
            }

            public Integer getPort() {
                return port;
            }

            public void setPort(Integer port) {
                this.port = port;
            }

            public Integer getDatabase() {
                return database;
            }

            public void setDatabase(Integer database) {
                this.database = database;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }
        }
        public enum CacheType {
            REDIS,
            MEMCACHED,
            EHCACHE
        }
    }
}
