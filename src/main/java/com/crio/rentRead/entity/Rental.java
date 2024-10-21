package com.crio.rentRead.entity;

import com.crio.rentRead.constants.RentalStatus;
import jakarta.persistence.*;
import lombok.Data;

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

        @ManyToOne(optional = false)
        @JoinColumn(name = "bookId", nullable = false)
        private Book book;

        @Enumerated(EnumType.STRING)
        private RentalStatus rentalStatus = RentalStatus.ACTIVE;

        public Rental() {}
}
