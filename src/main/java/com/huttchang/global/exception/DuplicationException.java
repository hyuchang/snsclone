package com.huttchang.global.exception;

public class DuplicationException extends Exception {


    public DuplicationException(){
        super("Duplicated Data ");
    }
    public DuplicationException(String message){
        super(" Duplicated Data ("+ message+")");
    }

}
