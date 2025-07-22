package com.geeklib.ether.common.resolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.common.QueryParams.Operator;
import org.apache.commons.lang3.StringUtils;

@Component
public class RestQueryParamsResolver implements HandlerMethodArgumentResolver{

    private static final Set<String> EXCLUDED_PARAMS = new HashSet<>(
        Arrays.asList("sort", "page", "size"));

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return QueryParams.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Map<String, String[]> parameterMap = webRequest.getParameterMap();
        QueryParams queryParams = new QueryParams();

        for (String paramName : parameterMap.keySet()) {

            // 跳过分页和排序参数
            if (EXCLUDED_PARAMS.contains(paramName)) {
                continue;
            }
            String[] values = parameterMap.get(paramName);
            String value = values[0];
            String key = paramName; // 默认使用原始参数名
            Operator operator = Operator.EQ; // 默认使用等于
    

            
            // 检查是否以枚举值结尾
            for (QueryParams.Operator op : QueryParams.Operator.values()) {
                if (paramName.endsWith("_" + op.name().toLowerCase())) {
                    // 使用 lastIndexOf 获取下划线位置，避免多次调用 substring
                    int underscoreIndex = paramName.lastIndexOf('_');
                    key = paramName.substring(0, underscoreIndex);
                    operator = op;
                    break;
                }
            }

            // 检查是否包含冒号分隔符
            int colonIndex = paramName.indexOf(':');
            if (colonIndex > 0 && colonIndex < paramName.length() - 1) {
                // 获取操作符部分
                String operatorStr = paramName.substring(colonIndex + 1);
                // 获取字段名部分
                key = paramName.substring(0, colonIndex);
                
                // 根据操作符字符串找到对应的Operator枚举
                for (QueryParams.Operator op : QueryParams.Operator.values()) {
                    if (op.name().equalsIgnoreCase(operatorStr)) {
                        operator = op;
                        break;
                    }
                }
            }

            queryParams.add(queryParams.new QueryParam(key, operator, value));
        }
        return queryParams;
    }
}
