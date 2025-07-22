package com.geeklib.ether.common.handler;

import java.util.HashMap;
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
import org.springframework.web.server.ResponseStatusException;

import com.geeklib.ether.common.exception.UniqueIndexViolationException;

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
    public ResponseEntity<Map<String, String>> processMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage()));
        logger.info("请求参数验证错误: " + errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Map<String, String>> processBindException(BindException e) {

        Map<String, String> errors = e.getFieldErrors().stream()
                .collect(Collectors.toMap(error -> error.getField(), error -> error.getDefaultMessage()));
        logger.info("请求参数绑定错误: " + errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> processConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = e.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage()
            ));
        logger.error("参数验证失败: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> processValidationException(ValidationException e) {
        Map<String, String> errors = new HashMap<String, String>();
        if (e.getCause() instanceof ConstraintViolationException) {
            ConstraintViolationException cvex = (ConstraintViolationException) e.getCause();
            errors = cvex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    violation -> violation.getMessage()
                ));
        }
        logger.error("验证异常: {}", errors);
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(UniqueIndexViolationException.class)
    public ResponseEntity<String> processUniqueIndexViolationException(UniqueIndexViolationException e) {
        logger.error("唯一索引约束冲突异常: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> processException(Exception e) {
        e.printStackTrace();
        // 如果是ResponseStatus异常，使用它的状态码
        if (e instanceof ResponseStatusException) {
            ResponseStatusException rse = (ResponseStatusException) e;
            return ResponseEntity.status(rse.getStatus()).body(e.getMessage());
        }
        return ResponseEntity.status(500).body("未处理的异常:" + e.toString());
    }
}
