package com.axelspringer.upday.web.rest.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by damien on 7/4/17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND,reason = "Resource not found")
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
