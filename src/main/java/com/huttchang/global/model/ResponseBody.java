package com.huttchang.global.model;

import lombok.Data;

@Data
public class ResponseBody<T> {

    public ResponseBody(){}

    public ResponseBody(SystemCode code){
        this.code = code.getCode();
        this.message = code.name();
    }

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
