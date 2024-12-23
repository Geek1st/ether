package com.geeklib.ether.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

public class ValidationAspect {
    
    @Before(value = "execution(** com.geekib.ether.*.controller.*.*(..))")
    public void JsrProcess(JoinPoint joinPoint){
        
    }
}
