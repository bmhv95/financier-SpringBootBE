package com.personal.project.exception;

import org.springframework.http.HttpStatus;

import static com.personal.project.exception.ExceptionMessageGroup.*;

public class ExceptionController {
    public static ResponseException notFound(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.NOT_FOUND);
    }

    public static ResponseException conflict(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.CONFLICT);
    }

    public static ResponseException badRequest(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.BAD_REQUEST);
    }

    public static ResponseException internalServerError(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
