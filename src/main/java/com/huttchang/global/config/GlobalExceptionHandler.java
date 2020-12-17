package com.huttchang.global.config;

import com.huttchang.global.exception.DataNotFoundException;
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
        final ResponseBody response = new ResponseBody(SystemCode.UNCAUGHT_EXCEPTION);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataNotFoundException.class)
    protected ResponseEntity<ResponseBody<DataNotFoundException>> handleMethodDatanotFoundException(DataNotFoundException e) {
        log.error("handleMethodUncaughtException", e);
        final ResponseBody response = new ResponseBody(SystemCode.DATA_NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
