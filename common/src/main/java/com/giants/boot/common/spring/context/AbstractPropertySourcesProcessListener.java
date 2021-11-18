package com.giants.boot.common.spring.context;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

/**
 * AbstractPropertySourcesProcessListener TODO
 * date time: 2021/11/18 15:08
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3
 */
public abstract class AbstractPropertySourcesProcessListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    private Map<String, Object> propertiesMap;
    private ConfigurableEnvironment environment;

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        this.environment = event.getEnvironment();
        PropertiesPropertySource propertiesPropertySource =
                (PropertiesPropertySource)this.environment.getPropertySources().get("giants-property-sources");
        if (propertiesPropertySource != null) {
            this.propertiesMap = propertiesPropertySource.getSource();
        }
        this.processPropertySources();
        Properties properties = this.convertToProperties();
        if (propertiesPropertySource == null && properties != null) {
            this.environment.getPropertySources().addFirst(new PropertiesPropertySource("giants-property-sources",
                    properties));
        }
    }

    protected abstract void processPropertySources();

    protected String getProperty(String key) {
        return this.environment.getProperty(key);
    }

    protected <T> T getProperty(String key, Class<T> targetType) {
        return this.environment.getProperty(key, targetType);
    }

    protected Map<String, Object> getPropertyMap(String key) {
        Map<String, Object> map = this.getProperty(key, Map.class);
        if (map == null) {
            map = new HashMap<>();
            String startKey = new StringBuilder(key).append('.').toString();
            for (PropertySource<?> propertySource : this.environment.getPropertySources()) {
                if (propertySource.getSource() instanceof Map) {
                    Map<String, Object> sourceMap = (Map<String, Object>)propertySource.getSource();
                    for (Map.Entry<String, Object> entry : sourceMap.entrySet()) {
                        if (entry.getKey().startsWith(startKey)) {
                            map.put(entry.getKey().replaceFirst(startKey, ""), entry.getValue());
                        }
                    }
                }
            }
        }
        if (map.isEmpty()) {
            return null;
        }
        return map;
    }

    protected void setProperty(String key, Object value) {
        if (this.propertiesMap == null) {
            this.propertiesMap = new Hashtable();
        }
        this.propertiesMap.put(key, value);
    }

    protected void setMapPropertiy(String key, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                this.setProperty(new StringBuilder(key).append('.').append(entry.getKey()).toString(), entry.getValue());
            }
        }
    }

    private Properties convertToProperties() {
        if (this.propertiesMap == null || this.propertiesMap.isEmpty()) {
            return null;
        }
        Properties properties = new Properties();
        for (Map.Entry<String, Object> entry : this.propertiesMap.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }
        return properties;
    }
}
