package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * 409
 */
@Getter
public class ResourceConflictException extends ApiException {

    private String title = "资源冲突";
    private HttpStatus status = HttpStatus.CONFLICT;

    public ResourceConflictException(String message) {
        super(message);
    }

    public ResourceConflictException(String message, String... args) {
        super(message, args);
    }
}
