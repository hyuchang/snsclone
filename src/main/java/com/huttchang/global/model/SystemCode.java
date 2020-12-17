package com.huttchang.global.model;

public enum SystemCode {

    OK(0, "OK"),
    DATA_NOT_FOUND(100, "Data Not Found"),
    INVALID_ARGUMENTS(101, "invalid arguments"),
    UNCAUGHT_EXCEPTION(199, "UNCAUGHT_EXCEPTION");
    private int code;
    private String message;

    SystemCode(int i, String message) {
        this.code = i;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}