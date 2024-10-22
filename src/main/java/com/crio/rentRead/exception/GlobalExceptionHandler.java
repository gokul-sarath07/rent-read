package com.crio.rentRead.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = "Invalid input: " + ex.getValue() + " is not a valid number.";

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyRentedException.class)
    public String handleBookAlreadyRentedException(BookAlreadyRentedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(BookNotFoundException.class)
    public String handleBookNotFoundException(BookNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(MaxActiveRentalsExceededException.class)
    public String handleMaxActiveRentalsExceededException(MaxActiveRentalsExceededException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(RentalNotFoundException.class)
    public String handleRentalNotFoundException(RentalNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public String handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
