package com.huttchang.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(){
        super("Invalid Email Or Password");
    }

}
