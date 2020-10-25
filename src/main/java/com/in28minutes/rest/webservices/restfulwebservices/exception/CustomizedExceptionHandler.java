package com.in28minutes.rest.webservices.restfulwebservices.exception;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserNameBirthEmptyException;
import com.in28minutes.rest.webservices.restfulwebservices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllException(Exception ex, WebRequest request){
        return getResponseEntity(ex.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleUserNotFoundException(UserNotFoundException ex, WebRequest request){
        return getResponseEntity(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity getResponseEntity(String message, String description, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), message, description);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(UserNameBirthEmptyException.class)
    public final ResponseEntity handleUserNameBirthEmptyException(UserNameBirthEmptyException ex, WebRequest request){
        return getResponseEntity(ex.getMessage(), request.getDescription(false), HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getResponseEntity("Validation Failed", ex.getBindingResult().toString(), HttpStatus.BAD_REQUEST);
    }

}
