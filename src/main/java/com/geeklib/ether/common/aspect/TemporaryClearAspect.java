package com.geeklib.ether.common.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TemporaryClearAspect {
    
    @After("execution(** com.geekib.ether.*.controller.*.createImage(..))")
    public void cleanTemporaryDir(){
        
    }
}
