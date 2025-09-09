package com.geeklib.ether.common.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.geeklib.ether.common.exception.ResourceConflictException;

/**
 * 检查路径和请求体的一致性
 *
 */
@Aspect
@Component
public class PathBodyConsistencyAspect {
    
    @Around("@annotation(org.springframework.web.bind.annotation.PutMapping) || @annotation(org.springframework.web.bind.annotation.PatchMapping)")
    public void checkPathBodyConsistency(ProceedingJoinPoint joinPoint) throws Throwable {
        
        Map<String, Object> pathParams = new HashMap<>();
        Object body = null;

        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        Method method = methodSignature.getMethod();
        Annotation[][] annotations = method.getParameterAnnotations();
        for(int i = 0; i < annotations.length; i++){
            for(Annotation annotation : annotations[i]){
                if(annotation instanceof org.springframework.web.bind.annotation.PathVariable){
                   pathParams.put(method.getParameters()[i].getName(), args[i]);
                }
                if(annotation instanceof org.springframework.web.bind.annotation.RequestBody){
                    body = args[i];
                }
            }
        }

        if (body != null) {
            for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
                String paramName = entry.getKey();
                Object pathValue = entry.getValue();
                
                try {
                    Field field = body.getClass().getDeclaredField(paramName);
                    field.setAccessible(true);
                    Object bodyValue = field.get(body);
                    
                    if (pathValue != null && !pathValue.equals(bodyValue)) {
                        throw new ResourceConflictException("Path参数{}与RequestBody字段值不匹配", paramName);
                    }
                } catch (NoSuchFieldException e) {
                    continue;
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("参数 " + paramName + " 无法访问", e);
                }
            }
        }
        
        joinPoint.proceed();
    }
}
