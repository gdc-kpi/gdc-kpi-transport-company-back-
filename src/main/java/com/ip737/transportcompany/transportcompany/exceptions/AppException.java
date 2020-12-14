package com.ip737.transportcompany.transportcompany.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    public AppException() {
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getHttpStatus() {
        return Optional.ofNullable(getClass().getAnnotation(ResponseStatus.class))
                .orElseGet(() -> AppException.class.getAnnotation(ResponseStatus.class))
                .value();
    }
}
