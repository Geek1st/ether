package com.geeklib.ether.common.config;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.geeklib.ether.common.handler.PrettyPrintHandler;
import com.geeklib.ether.common.interceptor.AccessLogInterceptor;
import com.geeklib.ether.common.interceptor.RequestIdInterceptor;
import com.geeklib.ether.common.resolver.RestQueryParamsResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private PrettyPrintHandler prettyPrintHandler;

    @Resource
    private RequestIdInterceptor requestIdInterceptor;

    @Resource
    private AccessLogInterceptor accessLogInterceptor;

    @Resource
    private RestQueryParamsResolver restQueryParamsResolver;
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(restQueryParamsResolver);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestIdInterceptor);
        registry.addInterceptor(accessLogInterceptor);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除默认的 MappingJackson2HttpMessageConverter
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        // 添加我们的 PrettyPrintHandler
        converters.add(prettyPrintHandler);
    }
}
