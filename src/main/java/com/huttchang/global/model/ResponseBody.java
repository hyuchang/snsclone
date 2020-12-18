package com.huttchang.global.model;

import lombok.Getter;

@Getter
public class ResponseBody<T> {

    public ResponseBody(){}

    public ResponseBody(T t){
        this.data = t;
    }

    public ResponseBody(SystemCode code, String message){
        this.code = code.getCode();
        this.message = message;
    }

    private T data;
    private int code = 0;
    private String message = "OK";
}
