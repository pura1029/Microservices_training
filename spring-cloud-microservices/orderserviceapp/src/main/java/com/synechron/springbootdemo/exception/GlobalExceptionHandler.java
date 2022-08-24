package com.synechron.springbootdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleException(Exception exception){
        exception.printStackTrace();
        return ResponseEntity.status(BAD_REQUEST).body("{\"message\": \""+exception.getMessage()+"\"}");
    }
}