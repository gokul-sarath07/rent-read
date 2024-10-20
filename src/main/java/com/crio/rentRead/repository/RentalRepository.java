package com.crio.rentRead.repository;

import com.crio.rentRead.constants.RentalStatus;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    int countByUserAndStatus(User user, RentalStatus status);
    Optional<Rental> findByUserAndBookAndStatus(User user, Book book, RentalStatus status);
}
