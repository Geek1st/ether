package com.geeklib.ether.util;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;

public class TemplateEngineUtil {

    private static Logger logger = LoggerFactory.getLogger(TemplateEngineUtil.class);

    private static PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}");

    /**
     * 使用map进行模板绑定
     * @param content 模板字符串
     * @param params 待绑定的参数
     * @return 绑定后的文本
     */
    public static String bind(String content, Map<String, Object> params) {
        return propertyPlaceholderHelper.replacePlaceholders(content,
                new PropertyPlaceholderHelper.PlaceholderResolver() {

                    public String resolvePlaceholder(String placeholderName) {
                        return params.get(placeholderName).toString();
                    }

                });
    }

    /**
     * 使用map进行模板绑定
     * @param resource 模板文件
     * @param params 待绑定的参数
     * @return 绑定后的文本
     */
    public static String bind(Resource resource, Map<String, Object> params) {

        String content = null;
        try {
            content = FileUtils.readFileToString(resource.getFile());

        } catch (IOException e) {
            logger.error("模板文件读取异常:" + resource.getFilename(), e);
        }

        return bind(content, params);
    }

    /**
     * 使用简单类型对象进行模板绑定
     * @param resource 模板文件
     * @param object 待绑定的对象
     * @return 绑定后的文本
     */
    public static String bind(Resource resource, Object object) {

        ObjectMapper objectMapper = new ObjectMapper();
        MapLikeType map = objectMapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Object.class);
        Map<String, Object> params = null;
        try {
            params = objectMapper.readValue(objectMapper.writeValueAsString(object), map);
        } catch (JsonProcessingException e) {
            logger.error("JSON解析转换异常，类型{1}，值：{2}", object.getClass(), object);
        }
        
        return bind(resource, params);
    }
}
