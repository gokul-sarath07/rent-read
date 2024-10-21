package com.crio.rentRead.controller;

import com.crio.rentRead.dto.BookRentalRequest;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.crio.rentRead.config.PathConstants.*;

@RestController
@RequestMapping(API_BASE_PATH)
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping(RENT_BOOK)
    public ResponseEntity<Rental> rentBook(@Valid @RequestBody BookRentalRequest rentalRequest) {
        Rental rental = rentalService.rentBook(rentalRequest.getUserId(), rentalRequest.getBookId());

        return ResponseEntity.ok(rental);
    }

    @PutMapping(RETURN_BOOK)
    public ResponseEntity<Rental> returnBook(@Valid @RequestBody BookRentalRequest rentalRequest) {
        Rental rental = rentalService.returnBook(rentalRequest.getUserId(), rentalRequest.getBookId());

        return ResponseEntity.ok(rental);
    }
}
