package com.crio.rentRead.repository;

import com.crio.rentRead.constants.RentalStatus;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    int countByUserAndRentalStatus(User user, RentalStatus status);

    @Query("SELECT r FROM Rental r WHERE r.user.email = ?1 AND r.book.bookId = ?2 AND r.rentalStatus = ?3")
    Optional<Rental> findByUserEmailAndBookIdAndStatus(String email, Long bookId, RentalStatus status);
}
