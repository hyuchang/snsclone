package com.huttchang.global.model;

public enum SystemCode {

    OK(0, "OK"),
    DATA_NOT_FOUND(100, "Data Not Found"),
    DATA_DUPLICATED(101, "Data Duplicated"),
    INVALID_ARGUMENTS(101, "Invalid arguments"),
    UNAUTHORIZED(102, "Unauthorized"),
    BLOCKED(103, "Blocked"),
    EXPIRED_TOKEN(104, "EXPIRED_TOKEN"),

    UNCAUGHT_EXCEPTION(199, "Uncaught exception");
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