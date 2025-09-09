package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
    private String title = "请求参数错误";
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String... args) {
        super(message, args);
    }
}
