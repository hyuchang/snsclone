package com.huttchang.global.config;

import com.huttchang.global.exception.AuthTokenExpiredException;
import com.huttchang.global.exception.DataNotFoundException;
import com.huttchang.global.exception.DuplicationException;
import com.huttchang.global.exception.UnauthorizedException;
import com.huttchang.global.model.ResponseBody;
import com.huttchang.global.model.SystemCode;
import com.huttchang.sns.account.exception.InvalidPasswordException;
import com.huttchang.sns.account.exception.UserBlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.UnexpectedTypeException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<ResponseBody<NullPointerException>> handleMethodUncaughtException(Exception e) {
        log.error("handleMethodUncaughtException", e);
        if (e instanceof UserBlockException) {
            return new ResponseEntity(new ResponseBody(SystemCode.BLOCKED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof InvalidPasswordException || e instanceof UnauthorizedException) {
            return new ResponseEntity(new ResponseBody(SystemCode.UNAUTHORIZED, e.getMessage()), HttpStatus.UNAUTHORIZED);
        } else if (e instanceof DuplicationException) {
            return new ResponseEntity(new ResponseBody(SystemCode.DATA_DUPLICATED, e.getMessage()), HttpStatus.BAD_REQUEST);
        } else if (e instanceof AuthTokenExpiredException) {
            return new ResponseEntity(new ResponseBody(SystemCode.EXPIRED_TOKEN, e.getMessage()), HttpStatus.BAD_REQUEST);
        } else if (e instanceof MethodArgumentNotValidException || e instanceof UnexpectedTypeException) {
            return new ResponseEntity(new ResponseBody(SystemCode.INVALID_ARGUMENTS, e.getMessage()), HttpStatus.BAD_REQUEST);
        } else if (e instanceof NoHandlerFoundException) {
            return new ResponseEntity(new ResponseBody(SystemCode.DATA_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(new ResponseBody(SystemCode.UNCAUGHT_EXCEPTION, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
