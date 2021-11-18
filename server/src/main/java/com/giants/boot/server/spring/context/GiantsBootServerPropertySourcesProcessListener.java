package com.giants.boot.server.spring.context;

import com.giants.boot.common.spring.context.AbstractPropertySourcesProcessListener;
import org.apache.commons.lang.StringUtils;

/**
 * GiantsBootServerPropertySourcesProcessListener TODO
 * date time: 2021/11/18 17:11
 * Copyright 2021 github.com/vencent-lu/giants-boot Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.3
 */
public class GiantsBootServerPropertySourcesProcessListener extends AbstractPropertySourcesProcessListener {

    @Override
    protected void processPropertySources() {
        String applicationName = this.getProperty("spring.application.name");
        String applicationVersion = this.getProperty("giants.boot.server.version");
        if (StringUtils.isNotEmpty(applicationName) && StringUtils.isNotEmpty(applicationVersion)) {
            this.setProperty("spring.application.name",
                    new StringBuilder(applicationName).append('-').append(applicationVersion.replace('.', '-')));
        }
    }

}
