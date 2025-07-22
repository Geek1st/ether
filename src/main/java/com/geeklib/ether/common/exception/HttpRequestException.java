package com.geeklib.ether.common.exception;


import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class HttpRequestException extends RuntimeException {
    private final HttpStatus status;

    public HttpRequestException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpRequestException(HttpStatus status, String message, String... args) {
        super(MessageFormatter.format(message, args).getMessage());
        this.status = status;
    }
}
