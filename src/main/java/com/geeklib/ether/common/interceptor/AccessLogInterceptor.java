package com.geeklib.ether.common.interceptor;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 访问日志拦截器
 */
@Component
public class AccessLogInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(AccessLogInterceptor.class);
    
    /**
     * 请求前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        long startTime = System.currentTimeMillis();
        
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode header = mapper.createObjectNode();
        Collections.list(request.getHeaderNames()).forEach(headerName -> header.put(headerName, request.getHeader(headerName)));
        
        logger.debug("=====>" + mapper.writeValueAsString(header));

        ObjectNode params = mapper.createObjectNode();
        request.getParameterMap().forEach((key, value) -> {
            params.put(key, value[0]);
        });
        
    
        logger.debug(mapper.writeValueAsString(params));
        
        // 将开始时间存储在请求属性中
        request.setAttribute("startTime", startTime);
        
        return true;
    }
    
    /**
     * 请求后处理
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestId = (String) request.getAttribute("requestId");
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        String uri = request.getRequestURI();
        int statusCode = response.getStatus();
        
        // 构建响应日志
        StringBuilder logBuilder = new StringBuilder("<====={\"type\":\"access\",\"stage\":\"end\",\"requestId\":\"" + requestId + "\",\"timestamp\":" + endTime + ",\"uri\":\"" + uri + "\",\"status\":" + statusCode + ",\"duration\":" + duration);
        
        // 如果有异常，记录异常信息
        if (ex != null) {
            logBuilder.append(",\"exception\":{\"type\":\"" + ex.getClass().getName() + "\",\"message\":\"" + ex.getMessage() + "\"}");
        }
        
        logBuilder.append("}");
        
        // 记录请求结束日志（JSON格式）
        logger.debug(logBuilder.toString());
    }
}
