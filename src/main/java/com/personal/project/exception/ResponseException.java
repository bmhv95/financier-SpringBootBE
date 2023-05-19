package com.personal.project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public class ResponseException extends RuntimeException{
    @Getter
    private ResponseExceptionBody responseExceptionBody;

    public ResponseException(String message, String messageKey, HttpStatus httpStatus) {
        this.responseExceptionBody = new ResponseExceptionBody(message, messageKey, httpStatus);
    }

    @Getter
    public static class ResponseExceptionBody{
        private String message;
        private String messageKey;
        private HttpStatus httpStatus;
        private String timestamp;
        @Setter
        private String webRequestURI;
        @Setter
        private String webRequestMethod;

        public ResponseExceptionBody(String message, String messageKey, HttpStatus httpStatus) {
            this.message = message;
            this.messageKey = messageKey;
            this.httpStatus = httpStatus;
            this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
    }
}
