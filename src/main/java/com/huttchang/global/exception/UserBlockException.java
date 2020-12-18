package com.huttchang.global.exception;

import org.springframework.http.HttpStatus;

public class UserBlockException extends Exception {

    public UserBlockException(){
        super("Blocking User");
    }

}
