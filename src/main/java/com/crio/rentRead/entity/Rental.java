package com.crio.rentRead.entity;

import com.crio.rentRead.constants.RentalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Rentals")
public class Rental {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long rentalId;

        @ManyToOne(optional = false)
        @JoinColumn(name = "userId", nullable = false)
        private User user;

        @ManyToOne(optional = false, cascade = CascadeType.MERGE)
        @JoinColumn(name = "bookId", nullable = false)
        private Book book;

        private LocalDateTime rentalDate = LocalDateTime.now();

        @Enumerated(EnumType.STRING)
        private RentalStatus rentalStatus = RentalStatus.ACTIVE;

        private LocalDateTime returnDate;

        public Rental() {}
}
