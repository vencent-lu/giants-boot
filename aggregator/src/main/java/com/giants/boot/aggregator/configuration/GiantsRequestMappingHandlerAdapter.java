package com.giants.boot.aggregator.configuration;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * GiantsRequestMappingHandlerAdapter TODO
 * date time: 2024/11/29 15:31
 * Copyright 2024 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.5.0
 */
public class GiantsRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
    public GiantsRequestMappingHandlerAdapter() {
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
