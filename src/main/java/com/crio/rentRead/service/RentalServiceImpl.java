package com.crio.rentRead.service;

import com.crio.rentRead.constants.BookStatus;
import com.crio.rentRead.constants.RentalStatus;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.exception.BookAlreadyRentedException;
import com.crio.rentRead.exception.MaxActiveRentalsExceededException;
import com.crio.rentRead.exception.RentalNotFoundException;
import com.crio.rentRead.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Override
    public Rental rentBook(Long userId, Long bookId) {
        User user = checkIfUserExceededActiveRentals(userId);
        Book book = checkIfBookIsAvailableForRent(bookId);
        checkIfUserAlreadyRentedSameBook(user.getUserId(), book.getBookId());

        updateBookAvailability(book, RentalStatus.ACTIVE);
        Rental rental = createRental(user, book);

        return rentalRepository.save(rental);
    }

    private void checkIfUserAlreadyRentedSameBook(Long userId, Long bookId) {
        Optional<Rental> alreadyTaken = rentalRepository.findByUserIdAndBookIdAndStatus(userId, bookId, RentalStatus.ACTIVE);

        if (alreadyTaken.isPresent()) {
            throw new BookAlreadyRentedException("User with id " + userId + " already rented the book with id " + bookId);
        }
    }

    private Rental createRental(User user, Book book) {
        Rental rental = new Rental();

        rental.setUser(user);
        rental.setBook(book);

        return rental;
    }

    private void updateBookAvailability(Book book, RentalStatus status) {
        int copiesAvailable = book.getCopiesAvailable();

        if (status == RentalStatus.ACTIVE) {
            copiesAvailable = Math.max(0, copiesAvailable - 1);
        } else if (status == RentalStatus.RETURNED) {
            copiesAvailable++;
        }

        book.setCopiesAvailable(copiesAvailable);

        if (copiesAvailable == 0) {
            book.setAvailabilityStatus(BookStatus.RENTED);
        } else {
            book.setAvailabilityStatus(BookStatus.AVAILABLE);
        }
    }

    private Book checkIfBookIsAvailableForRent(Long bookId) {
        Book book = bookService.getBook(bookId);
        if (book.getAvailabilityStatus() == BookStatus.RENTED) {
            throw new BookAlreadyRentedException("The book, " + book.getTitle() + ", is not available for rent. Please come back later.");
        }

        return book;
    }

    private User checkIfUserExceededActiveRentals(Long userId) {
        User user = userService.getUser(userId);
        int activeRentals = rentalRepository.countByUserAndRentalStatus(user, RentalStatus.ACTIVE);
        if (activeRentals >= 2) {
            throw new MaxActiveRentalsExceededException("User has reached the maximum number of active rentals. Please return a rented book to get this one.");
        }

        return user;
    }

    @Override
    public Rental returnBook(Long userId, Long bookId) {
        Rental rental = rentalRepository.findByUserIdAndBookIdAndStatus(userId, bookId, RentalStatus.ACTIVE)
                .orElseThrow(() -> new RentalNotFoundException("User with id " + userId + " has not rented book with id " + bookId));

        Book book = rental.getBook();
        updateBookAvailability(book, RentalStatus.RETURNED);

        rental.setRentalStatus(RentalStatus.RETURNED);
        rental.setReturnDate(LocalDateTime.now());

        return rentalRepository.save(rental);
    }
}
