package com.geeklib.ether.common.exception;

import org.springframework.http.HttpStatus;

/**
 * 410 Gone
 */
public class ResourceGoneException extends ApiException {

    private String title = "资源永久失效";
    private String type = "http://www.baidu.com";
    private HttpStatus status = HttpStatus.GONE;

    public ResourceGoneException(String message) {
        super(message);
    }
}
