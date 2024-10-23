package com.crio.rentRead.service;

import com.crio.rentRead.entity.Rental;

public interface RentalService {
    Rental rentBook(Long bookId);
    Rental returnBook(Long bookId);
}
