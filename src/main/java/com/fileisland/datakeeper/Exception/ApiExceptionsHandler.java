package com.fileisland.datakeeper.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionsHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity handleApiRequestException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
