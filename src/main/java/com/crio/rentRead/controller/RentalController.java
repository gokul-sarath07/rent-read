package com.crio.rentRead.controller;

import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.crio.rentRead.config.PathConstants.RENT_BOOK;
import static com.crio.rentRead.config.PathConstants.RETURN_BOOK;

@RestController
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping(RENT_BOOK)
    public ResponseEntity<Rental> rentBook(@PathVariable("bookId") Long bookId) {
        Rental rental = rentalService.rentBook(bookId);

        return ResponseEntity.ok(rental);
    }

    @PutMapping(RETURN_BOOK)
    public ResponseEntity<Rental> returnBook(@PathVariable("bookId") Long bookId) {
        Rental rental = rentalService.returnBook(bookId);

        return ResponseEntity.ok(rental);
    }
}
