package com.huttchang.global.exception;

public class DataNotFoundException extends Exception {

    public DataNotFoundException(){
        super("Data Not Found");
    }
    public DataNotFoundException(String message){
        super(message);
    }

}
