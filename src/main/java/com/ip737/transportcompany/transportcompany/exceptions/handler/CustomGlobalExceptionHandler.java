package com.ip737.transportcompany.transportcompany.exceptions.handler;

import com.ip737.transportcompany.transportcompany.exceptions.AppException;
import com.ip737.transportcompany.transportcompany.exceptions.IdentificationException;
import com.ip737.transportcompany.transportcompany.exceptions.ValidationException;
import com.ip737.transportcompany.transportcompany.exceptions.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ErrorMessage> handleException(AppException exception,
                                                        HttpServletRequest request){

        HttpStatus httpStatus = exception.getHttpStatus();

        log.error(String.format("Exception received, path: '%s'\nException: '%s'",
                request.getRequestURI(), exception));
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(exception.getMessage())
                .statusCode(httpStatus.value())
                .timestamp(System.currentTimeMillis())
                .error(exception.getClass().getName())
                .requestPath(request.getRequestURI())
                .build();

        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}
