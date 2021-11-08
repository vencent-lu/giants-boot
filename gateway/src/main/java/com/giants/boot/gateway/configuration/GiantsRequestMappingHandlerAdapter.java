package com.giants.boot.gateway.configuration;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * GiantsRequestMappingHandlerAdapter TODO
 * date time: 2021/11/4 15:42
 * Copyright 2021 www.meikuangrm.com Inc. All rights reserved.
 *
 * @author vencent-lu
 * @since 1.2
 */
public class GiantsRequestMappingHandlerAdapter extends RequestMappingHandlerAdapter {
    public GiantsRequestMappingHandlerAdapter() {
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
