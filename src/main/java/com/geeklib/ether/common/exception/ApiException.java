package com.geeklib.ether.common.exception;

import org.slf4j.helpers.MessageFormatter;
import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException extends RuntimeException {

    private HttpStatus status;
    private String message;
    private String title;
    private String type;

    public ApiException(String message) {
        
        super(message);
        this.message = message;
    }

    public ApiException(String message, String... args) {
        this(MessageFormatter.arrayFormat(message, args).getMessage());
        this.message = MessageFormatter.arrayFormat(message, args).getMessage();
    }

}
