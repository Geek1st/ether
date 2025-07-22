package com.geeklib.ether.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.config.IndexType;

@Retention(RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.FIELD)
public @interface HazelcastIndex {

    IndexType type() default IndexType.HASH;
    Class<?> unique() default Void.class; // 用于唯一索引的分组类，如果不指定则不创建唯一索引
}
