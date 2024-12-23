package com.geeklib.ether.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.geeklib.ether.annotation.API;

public class PlaceholderUtil {

    public String processPlaceholdes(Method method, Object[] objects){
        Parameter[] parameters = method.getParameters();
        String result = null;

        for(int i = 0; i < parameters.length; i++){
            API annocation = parameters[i].getAnnotation(API.class);
            if( null != annocation){
                String url = annocation.value();
            }
        }
        return result;
    }

}
