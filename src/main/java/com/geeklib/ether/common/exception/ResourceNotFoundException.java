package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 404
 */
@Getter
public class ResourceNotFoundException extends ApiException {

    private String title = "资源不存在";
    private HttpStatus status = HttpStatus.NOT_FOUND;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, String... args) {
        super(message, args);
    }
}
