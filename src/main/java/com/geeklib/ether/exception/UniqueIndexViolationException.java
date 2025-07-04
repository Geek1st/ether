package com.geeklib.ether.exception;

import java.lang.reflect.Field;
import java.text.MessageFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * 唯一索引约束冲突异常
 */
@Getter
public class UniqueIndexViolationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Class<?> clazz;

    private Field field;

    private Object value;

    public UniqueIndexViolationException(Class clazz, Field field, Object value) {
        super(new MessageFormat("唯一索引约束冲突异常，类:''{0}'', 属性:''{1}'', 值:''{2}''").format(new Object[] { clazz.getName(), field.getName(), value }));
        this.clazz = clazz;
        this.field = field;
        this.value = value;
    }
}