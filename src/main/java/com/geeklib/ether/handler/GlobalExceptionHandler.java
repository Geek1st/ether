package com.geeklib.ether.handler;

import org.apache.catalina.connector.Response;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    

    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<Object> processHttpResponseException(HttpResponseException e){
        logger.error("Jenkins API调用异常", e);
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void exceptionHandler(Exception e){
        
        logger.error("",e);
    }
}
