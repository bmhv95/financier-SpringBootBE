package com.personal.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static com.personal.project.exception.ExceptionMessageGroup.*;

@Slf4j
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

    public static ResponseException unauthorized(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.UNAUTHORIZED);
    }

    public static ResponseException forbidden(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.FORBIDDEN);
    }

    public static ResponseException internalServerError(String message, String messageKey) {
        return new ResponseException(message, messageKey, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseException accountNotFound(String accountID) {
        log.warn(ACCOUNT_NOT_FOUND_MESSAGE + " : " + accountID);
        return new ResponseException(ACCOUNT_NOT_FOUND_MESSAGE, ACCOUNT_NOT_FOUND_MESSAGE_KEY, HttpStatus.NOT_FOUND);
    }

    public static ResponseException walletNotFound(Long walletID){
        log.warn(WALLET_NOT_FOUND_MESSAGE + " : " + walletID);
        return new ResponseException(WALLET_NOT_FOUND_MESSAGE, WALLET_NOT_FOUND_MESSAGE_KEY, HttpStatus.NOT_FOUND);
    }

    public static ResponseException envelopeNotFound(Long envelopeID){
        log.warn(ENVELOPE_NOT_FOUND_MESSAGE + " : " + envelopeID);
        return new ResponseException(ENVELOPE_NOT_FOUND_MESSAGE, ENVELOPE_NOT_FOUND_MESSAGE_KEY, HttpStatus.NOT_FOUND);
    }

    public static ResponseException goalNotFound(Long goalID){
        log.warn(GOAL_NOT_FOUND_MESSAGE + " : " + goalID);
        return new ResponseException(GOAL_NOT_FOUND_MESSAGE, GOAL_NOT_FOUND_MESSAGE_KEY, HttpStatus.NOT_FOUND);
    }

    public static ResponseException transactionNotFound(Long transactionID){
        log.warn(TRANSACTION_NOT_FOUND_MESSAGE + " : " + transactionID);
        return new ResponseException(TRANSACTION_NOT_FOUND_MESSAGE, TRANSACTION_NOT_FOUND_MESSAGE_KEY, HttpStatus.NOT_FOUND);
    }

    public static ResponseException accountExisted(String email){
        log.warn(ACCOUNT_EXISTED_MESSAGE + " : " + email);
        return new ResponseException(ACCOUNT_EXISTED_MESSAGE, ACCOUNT_EXISTED_MESSAGE_KEY, HttpStatus.CONFLICT);
    }

    public static ResponseException forbidden(){
        return new ResponseException(FORBIDDEN_MESSAGE, FORBIDDEN_MESSAGE_KEY, HttpStatus.FORBIDDEN);
    }
}
