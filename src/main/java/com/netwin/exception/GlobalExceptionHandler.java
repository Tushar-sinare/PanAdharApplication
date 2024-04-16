package com.netwin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String>resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
       
        return new ResponseEntity<String>(ex.resourceName,HttpStatus.NOT_FOUND);
    }
}
