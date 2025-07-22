package com.geeklib.ether.common;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * 查询参数包装类
 */
@Getter
@Setter
public class QueryParams extends ArrayList<QueryParams.QueryParam> {

    public QueryParams() {
        super();
    }

    @Getter
    @Setter
    public class QueryParam {
        private String key;
        private Operator operator;
        private String value;

        public QueryParam(String key, Operator operator, String value) {
            this.key = key;
            this.operator = operator;
            this.value = value;
        }
    }

    /**
     * 查询操作符枚举
     */
    public enum Operator {
        EQ("eq"),
        NE("ne"),
        GT("gt"),
        LT("lt"),
        LIKE("like"),
        IN("in"),
        IS_NULL("is_null");

        private final String operator;

        Operator(String operator) {
            this.operator = operator;
        }

        public String getOperator() {
            return operator;
        }

    }

}