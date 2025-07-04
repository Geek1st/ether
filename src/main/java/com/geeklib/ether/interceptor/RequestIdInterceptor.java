package com.geeklib.ether.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestIdInterceptor implements HandlerInterceptor {

    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String REQUEST_ID_ATTRIBUTE = "requestId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 优先使用前端传入的RequestId
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null) {
            // 如果前端没有传入，生成一个新的RequestId
            requestId = "backend-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
        }
        
        request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
        MDC.put(REQUEST_ID_ATTRIBUTE, " [" + requestId + "]");
        
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        MDC.remove(REQUEST_ID_ATTRIBUTE);
    }
}
