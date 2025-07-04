package com.geeklib.ether.exception;

import org.springframework.http.HttpStatus;

/**
 * 409
 */
public class ResourceConflictException extends HttpRequestException {

    public ResourceConflictException(String message, String... args) {
        super(HttpStatus.CONFLICT, message, args);
    }
    
    public ResourceConflictException() {
        super(HttpStatus.CONFLICT, "资源冲突");
    }

    public ResourceConflictException(String key) {
        super(HttpStatus.CONFLICT, "资源{}已存在，无法进行操作", key);
    }
}
