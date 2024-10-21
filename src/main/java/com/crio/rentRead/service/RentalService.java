package com.crio.rentRead.service;

import com.crio.rentRead.entity.Rental;

public interface RentalService {
    Rental rentBook(Long userId, Long bookId);
    Rental returnBook(Long userId, Long bookId);
}
