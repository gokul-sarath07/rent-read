package com.crio.rentRead.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookAlreadyRentedException extends RuntimeException {
    public BookAlreadyRentedException() {}

    public BookAlreadyRentedException(String message) {
        super(message);
    }
}
