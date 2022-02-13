package com.georgeciachir.appforcloud.controller;

import com.georgeciachir.appforcloud.exception.TemplateLocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {

    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(TemplateLocationException.class)
    public ResponseEntity<String> handle(TemplateLocationException exception) {
        log.error("Trouble: ", exception);
        return ResponseEntity.internalServerError()
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception exception) {
        log.error("Trouble: ", exception);
        return ResponseEntity.internalServerError()
                .body(exception.getMessage());
    }
}
