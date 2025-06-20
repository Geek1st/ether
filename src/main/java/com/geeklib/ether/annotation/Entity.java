package com.geeklib.ether.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 标记为 Entity 的注解
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target (java.lang.annotation.ElementType.TYPE)
public @interface Entity {
    String name() default "";
}
