package com.huttchang.global.exception;

public class AuthTokenExpiredException extends Exception{
    public AuthTokenExpiredException(String message) {
        super(message);
    }
}
