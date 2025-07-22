package com.geeklib.ether.common.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.geeklib.ether.common.BaseEntity;

@Aspect
@Component
public class EntityTimeAspect {
    @Before("execution(* com.geeklib.ether..*.create*(..))")
    public void setCreateTime(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseEntity) {
                LocalDateTime now = LocalDateTime.now();
                BaseEntity entity = (BaseEntity) arg;
                entity.setGmtCreate(now);
                entity.setGmtModified(now);
            }
        }
    }

    @Before("execution(* com.geeklib.ether..*.update*(..)) || execution(* com.geeklib.ether..*.patch*(..))")
    public void setUpdateTime(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BaseEntity) {
                ((BaseEntity) arg).setGmtModified(LocalDateTime.now());
            }
        }
    }
}