package com.geeklib.ether.common.utils;

public class StringUtils {
    
    /**
     * 驼峰转下划线
     * @param str 字符串
     * @return 下划线格式的字符串
     */
    public static String camelToUnderline(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                if (result.length() > 0) {
                    result.append('_');
                }
                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    /**
     *  将驼峰命名法的字符串转换为下划线命名法
     * 例如：helloWorld -> hello_world
     * @param str 字符串
     * @return 下划线格式的字符串
     */
    public static String toUnderline(String str) {
         return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }
}
