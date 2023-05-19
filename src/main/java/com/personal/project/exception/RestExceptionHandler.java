package com.personal.project.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<Object> handleResponseException(ResponseException ex, WebRequest request) {
        ex.getResponseExceptionBody().setWebRequestURI(request.getDescription(false));
        ex.getResponseExceptionBody().setWebRequestMethod(((ServletWebRequest)request).getRequest().getMethod());

        return new ResponseEntity<>(ex.getResponseExceptionBody(), new HttpHeaders(), ex.getResponseExceptionBody().getHttpStatus());
    }
}
