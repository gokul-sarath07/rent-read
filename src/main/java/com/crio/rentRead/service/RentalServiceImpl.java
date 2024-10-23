package com.crio.rentRead.service;

import com.crio.rentRead.constants.BookStatus;
import com.crio.rentRead.constants.RentalStatus;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.exception.BookAlreadyRentedException;
import com.crio.rentRead.exception.MaxActiveRentalsExceededException;
import com.crio.rentRead.exception.RentalNotFoundException;
import com.crio.rentRead.exception.UserNotFoundException;
import com.crio.rentRead.repository.RentalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Override
    public Rental rentBook(Long bookId) {
        log.info("Entered rentBook() method - bookId: {}", bookId);
        User user = userService.getUserByEmail(getCurrentUserEmail());
        checkIfUserExceededActiveRentals(user);
        Book book = checkIfBookIsAvailableForRent(bookId);
        checkIfUserAlreadyRentedSameBook(user.getEmail(), book.getBookId());

        updateBookAvailability(book, RentalStatus.ACTIVE);
        Rental rental = createRental(user, book);

        log.info("Book rental object created: {}", rental);

        return rentalRepository.save(rental);
    }

    private String getCurrentUserEmail() {
        log.info("Entered getCurrentUserEmail() method");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User is not logged in");
            throw new UserNotFoundException("User not logged in.");
        }

        log.info("Email of current active user is {}", authentication.getName());

        return authentication.getName();
    }

    private void checkIfUserAlreadyRentedSameBook(String email, Long bookId) {
        log.info("Entered checkIfUserAlreadyRentedSameBook() method - email: {}, bookId: {}", email, bookId);
        Optional<Rental> alreadyTaken = rentalRepository.findByUserEmailAndBookIdAndStatus(email, bookId, RentalStatus.ACTIVE);

        if (alreadyTaken.isPresent()) {
            log.error("Book with id: {} has already rented by user: {}", bookId, email);
            throw new BookAlreadyRentedException("User with email " + email + " already rented the book with id " + bookId);
        }

        log.info("Book with id: {} has not been rented by user: {}", bookId, email);
    }

    private Rental createRental(User user, Book book) {
        log.info("Entered createRental() method");
        Rental rental = new Rental();

        rental.setUser(user);
        rental.setBook(book);

        return rental;
    }

    private void updateBookAvailability(Book book, RentalStatus status) {
        log.info("Entered updateBookAvailability() method - book: {} and status: {}", book, status);
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

        log.info("Book with id: {} has {} copies left and with status {}", book.getBookId(), book.getCopiesAvailable(), book.getAvailabilityStatus());
    }

    private Book checkIfBookIsAvailableForRent(Long bookId) {
        log.info("Entered checkIfBookIsAvailableForRent() method - bookId: {}", bookId);
        Book book = bookService.getBook(bookId);
        if (book.getAvailabilityStatus() == BookStatus.RENTED) {
            log.warn("Book with id: {} is not available for rent", bookId);
            throw new BookAlreadyRentedException("The book, " + book.getTitle() + ", is not available for rent. Please come back later.");
        }

        log.info("Book with id: {} is available for rent", bookId);
        return book;
    }

    private void checkIfUserExceededActiveRentals(User user) {
        log.info("Entered checkIfUserExceededActiveRentals() method");
        int activeRentals = rentalRepository.countByUserAndRentalStatus(user, RentalStatus.ACTIVE);
        if (activeRentals >= 2) {
            log.warn("User has exceeded the active rental limit - userId: {}", user.getUserId());
            throw new MaxActiveRentalsExceededException("User has reached the maximum number of active rentals. Please return a rented book to get this one.");
        }

        log.debug("User with id: {} has active rentals {}", user.getUserId(), activeRentals);
    }

    @Override
    public Rental returnBook(Long bookId) {
        log.info("Entered returnBook() method - bookId: {}", bookId);
        String email = getCurrentUserEmail();
        Rental rental = rentalRepository.findByUserEmailAndBookIdAndStatus(email, bookId, RentalStatus.ACTIVE)
                .orElseThrow(() -> new RentalNotFoundException("User with email " + email + " has not rented book with id " + bookId));
        log.info("Received user email: {} and users rental info with active status: {}", email, rental);
        Book book = rental.getBook();
        updateBookAvailability(book, RentalStatus.RETURNED);

        rental.setRentalStatus(RentalStatus.RETURNED);
        rental.setReturnDate(LocalDateTime.now());
        log.info("Rental object status set to {} with date {}", rental.getRentalStatus(), rental.getReturnDate());

        return rentalRepository.save(rental);
    }
}
