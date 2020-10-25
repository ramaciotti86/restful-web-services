package com.in28minutes.rest.webservices.restfulwebservices.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIdPostEmptyException extends RuntimeException {

    public UserIdPostEmptyException(String message) {
        super(message);
    }
}
