package com.crio.rentRead.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MaxActiveRentalsExceededException extends RuntimeException {
    public MaxActiveRentalsExceededException() {}

    public MaxActiveRentalsExceededException(String message) {
        super(message);
    }
}
