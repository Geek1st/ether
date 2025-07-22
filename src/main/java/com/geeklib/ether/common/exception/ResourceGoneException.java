package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 410 Gone
 */
public class ResourceGoneException extends HttpRequestException {

    public ResourceGoneException(String message, String... args) {
        super(HttpStatus.GONE, message, args);
    }

    public ResourceGoneException() {
        super(HttpStatus.GONE, "资源已被删除或不存在");
    }

    public ResourceGoneException(String key) {
        super(HttpStatus.GONE, "资源{}已被删除或不存在，无法进行操作", key);
    }
}
