package com.geeklib.ether.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.geeklib.ether.common.exception.HttpRequestException;

@RestControllerAdvice
@Order(1)
public class RestExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<String> processHttpRequestException(HttpRequestException e) {
        logger.error(e.toString());
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
