package com.fileisland.datakeeper.Exception;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.core.exception.SdkClientException;

@RestControllerAdvice
public class ApiExceptionsHandler {

    Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ApiExceptionsHandler.class);

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity handleApiRequestException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(value = {SdkClientException.class})
    public ResponseEntity handleApiRequestException(SdkClientException e) {
        LOGGER.error("Error while connecting to S3");
        LOGGER.debug(e.getMessage(), e);
        return ResponseEntity.internalServerError().build();
    }


}
