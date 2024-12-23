package com.geeklib.ether.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    
    // @ExceptionHandler
    // public ResponseEntity<String> processException(Exception e){
    //     e.printStackTrace();
    //     return ResponseEntity.status(500).body("未处理的异常:" + e.toString());
    // }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> processMethodArgumentNotVaildException(MethodArgumentNotValidException e){

        logger.error(null, e);
        return ResponseEntity.internalServerError().body("");
    }
}
