package com.huttchang.sns.account.exception;

import org.springframework.http.HttpStatus;

public class UserBlockException extends Exception {

    public UserBlockException(){
        super("Blocking User");
    }

}
