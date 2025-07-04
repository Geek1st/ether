package com.geeklib.ether.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {

    public static <T> Map<String, Object> toMap(Object obj) throws IllegalArgumentException, IllegalAccessException {

        Class<? extends Object> clazz = obj.getClass();
        Map<String, Object> params = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            // if(field.getType().isAssignableFrom(Quantity.class)){
            // Quantity quantity = (Quantity)field.get(obj);
            // params.put(field.getName(), quantity.getNumber().toString());
            // continue;
            // }
            params.put(field.getName(), field.get(obj));
        }
        return params;
    }

    /**
     * 将 src 中的非 null 属性值复制到 dest 中
     *
     * @param src  源对象
     * @param dest 目标对象
     * @return 目标对象
     */
    public static <T> T patchBean(T src, T dest) {
        Field[] fields = src.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            
            try {
                Object value = field.get(src);
                if (value != null) {
                    field.set(dest, value);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Error patching bean: " + e.getMessage(), e);
            }

        }
        return dest;
    }

}
