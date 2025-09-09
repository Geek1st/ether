package com.geeklib.ether.common.exception;

import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProblemDetail {
    private String type;
    private String title;
    private int status;
    private String detail;
    private String instance;

    public static ProblemDetail forException(ApiException e, HttpServletRequest request) {
        return new ProblemDetail(e.getMessage(),e.getTitle(), e.getStatus().value(), request.getRequestURI());
    }

    public ProblemDetail(String detail,String title, int status, String instance) {
        this.type = "https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Reference/Status/" + status;
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.instance = instance;
    }

    private ProblemDetail(){}

    public static ProblemDetail build(){
        return new ProblemDetail();
    }

    public ProblemDetail setTitle(String title){
        this.title = title;
        return this;
    }

    public ProblemDetail setStatus(int status){
        this.status = status;
        return this;
    }

    public ProblemDetail setDetail(String detail){
        this.detail = detail;
        return this;
    }

    public ProblemDetail setInstance(String instance){
        this.instance = instance;
        return this;
    }
}
