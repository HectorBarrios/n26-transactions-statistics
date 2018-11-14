package com.n26.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestBadRequestHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex.getCause().getCause() instanceof java.time.format.DateTimeParseException)
            return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);
        else if (ex.getCause() instanceof InvalidFormatException)
            return new ResponseEntity(null, HttpStatus.UNPROCESSABLE_ENTITY);

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

}
