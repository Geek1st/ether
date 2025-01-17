package com.geeklib.ether.exception;

import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Map<String, String>> processMethodArgumentNotVaildException(BindException e) {

        Map<String, String> errors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage()));

        return ResponseEntity.internalServerError().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<String> processException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("未处理的异常:" + e.toString());
    }
}
