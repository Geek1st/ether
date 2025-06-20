package com.geeklib.ether.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpServiceException extends RuntimeException{
    private int statusCode;
    private String responseBody;

    public HttpServiceException(int statusCode, String message, String responseBody) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }
}
