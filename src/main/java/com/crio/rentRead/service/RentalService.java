package com.crio.rentRead.service;

public interface RentalService {
    void rentBook(Long userId, Long bookId);
    void returnBook(Long userId, Long bookId);
}
