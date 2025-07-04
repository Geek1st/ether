package com.geeklib.ether.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.geeklib.ether.interceptor.AccessLogInterceptor;
import com.geeklib.ether.interceptor.RequestIdInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Resource
    private RequestIdInterceptor requestIdInterceptor;

    @Resource
    private AccessLogInterceptor accessLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestIdInterceptor);
        registry.addInterceptor(accessLogInterceptor);
    }
}
