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
            //     Quantity quantity = (Quantity)field.get(obj);
            //     params.put(field.getName(), quantity.getNumber().toString());
            //     continue;
            // }
            params.put(field.getName(), field.get(obj));
        }
        return params;
    }

}
