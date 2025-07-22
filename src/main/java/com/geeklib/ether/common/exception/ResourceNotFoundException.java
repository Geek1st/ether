package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

/** 
 * 404 
 */
public class ResourceNotFoundException extends HttpRequestException {

    public ResourceNotFoundException(String message, String... args) {
        super(HttpStatus.NOT_FOUND, message, args);
    }

    public ResourceNotFoundException(){
        super(HttpStatus.NOT_FOUND, "资源未找到");
    }

    public ResourceNotFoundException(String key) {
        super(HttpStatus.NOT_FOUND, "资源{}不存在", key);
    }
}
