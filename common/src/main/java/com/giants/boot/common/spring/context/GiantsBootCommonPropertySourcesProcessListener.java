package com.giants.boot.common.spring.context;

import java.util.Map;

/**
 * GiantsBootCommonPropertySourcesProcessListener TODO
 * date time: 2021/11/18 16:50
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3
 */
public class GiantsBootCommonPropertySourcesProcessListener extends AbstractPropertySourcesProcessListener {

    @Override
    protected void processPropertySources() {
        Integer bootFeignResponseExceptionStatus =
                this.getProperty("giants.boot.feign-config.response-exception-status", Integer.class);
        Map<String, Object> bootFeignClientVersionMap =
                this.getPropertyMap("giants.boot.feign-config.client-version-map");
        Integer feignResponseExceptionStatus =
                this.getProperty("giants.feign.response-exception-status", Integer.class);
        Map<String, Object> feignClientVersionMap =
                this.getPropertyMap("giants.feign.client-version-map");
        if (bootFeignResponseExceptionStatus != null && feignResponseExceptionStatus == null) {
            this.setProperty("giants.feign.response-exception-status", bootFeignResponseExceptionStatus);
        }
        if (bootFeignClientVersionMap != null && feignClientVersionMap == null) {
            this.setMapPropertiy("giants.feign.client-version-map", bootFeignClientVersionMap);
        }
    }

}
