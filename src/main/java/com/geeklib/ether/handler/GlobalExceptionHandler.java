package com.geeklib.ether.handler;

import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.geeklib.ether.exception.UniqueIndexViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        String message = e.getMostSpecificCause() != null
                ? e.getMostSpecificCause().getMessage()
                : e.getMessage();
        return ResponseEntity.badRequest().body("参数格式错误: " + message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        //TODO 优化输出
        logger.error(e.toString());
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Map<String, String>> processBindException(BindException e) {

        Map<String, String> errors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage()));

        return ResponseEntity.internalServerError().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> processConstraintViolationException(ConstraintViolationException e) {
        logger.error(e.toString());
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> processValidationException(ValidationException e) {
        logger.error(e.toString());
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UniqueIndexViolationException.class)
    public ResponseEntity<String> processUniqueIndexViolationException(UniqueIndexViolationException e) {
        logger.error("唯一索引约束冲突异常: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> processException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("未处理的异常:" + e.toString());
    }
}
