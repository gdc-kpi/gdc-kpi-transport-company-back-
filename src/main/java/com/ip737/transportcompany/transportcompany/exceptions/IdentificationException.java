package com.ip737.transportcompany.transportcompany.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class IdentificationException extends AppException{

    public IdentificationException(){}

    public IdentificationException(String message) {
        super(message);
    }

    public IdentificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
