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

    @Query("SELECT r FROM Rentals r WHERE r.user.userId = ?1 AND r.book.bookId = ?2 AND r.rentalStatus = ?3")
    Optional<Rental> findByUserIdAndBookIdAndStatus(Long userId, Long bookId, RentalStatus status);
}
