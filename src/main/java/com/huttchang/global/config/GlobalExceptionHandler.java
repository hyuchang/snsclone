package com.huttchang.global.config;

import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.global.exception.InvalidPasswordException;
import com.huttchang.global.exception.UserBlockException;
import com.huttchang.global.model.ResponseBody;
import com.huttchang.global.model.SystemCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseBody<NullPointerException>> handleMethodUncaughtException(Exception e) {
        log.error("handleMethodUncaughtException", e);
        if (e instanceof UserBlockException) {
            return new ResponseEntity(new ResponseBody(SystemCode.BLOCKED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof InvalidPasswordException) {
            return new ResponseEntity(new ResponseBody(SystemCode.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof DuplicationException) {
            return new ResponseEntity(new ResponseBody(SystemCode.DATA_DUPLICATED, e.getMessage()), HttpStatus.BAD_REQUEST);
        } else if (e instanceof DataNotFoundException) {
            return new ResponseEntity(new ResponseBody(SystemCode.DATA_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(new ResponseBody(SystemCode.UNCAUGHT_EXCEPTION, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
