package com.crio.rentRead.service;

import com.crio.rentRead.constants.BookStatus;
import com.crio.rentRead.constants.RentalStatus;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.entity.Rental;
import com.crio.rentRead.entity.User;
import com.crio.rentRead.exception.MaxActiveRentalsExceededException;
import com.crio.rentRead.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private User testUser;
    private Book testBook;
    private Rental testRental;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setUserId(1L);
        testUser.setEmail("testuser@gmail.com");

        testBook = new Book();
        testBook.setBookId(1L);
        testBook.setCopiesAvailable(1);
        testBook.setAvailabilityStatus(BookStatus.AVAILABLE);

        testRental = new Rental();
        testRental.setUser(testUser);
        testRental.setBook(testBook);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testRentBook_Success() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testuser@gmail.com");
        when(userService.getUserByEmail("testuser@gmail.com")).thenReturn(testUser);
        when(bookService.getBook(1L)).thenReturn(testBook);
        when(rentalRepository.findByUserEmailAndBookIdAndStatus("testuser@gmail.com", 1L, RentalStatus.ACTIVE))
                .thenReturn(Optional.empty());
        when(rentalRepository.countByUserAndRentalStatus(testUser, RentalStatus.ACTIVE)).thenReturn(1);
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);

        Rental rental = rentalService.rentBook(1L);

        assertNotNull(rental);
        assertEquals(testUser, rental.getUser());
        assertEquals(testBook, rental.getBook());
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    void testRentBook_MaxActiveRentalsExceeded() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testuser@gmail.com");
        when(userService.getUserByEmail("testuser@gmail.com")).thenReturn(testUser);
        when(rentalRepository.countByUserAndRentalStatus(testUser, RentalStatus.ACTIVE)).thenReturn(2);

        Exception exception = assertThrows(MaxActiveRentalsExceededException.class, () -> {
            rentalService.rentBook(1L);
        });

        assertEquals("User has reached the maximum number of active rentals. Please return a rented book to get this one.", exception.getMessage());
        verify(rentalRepository, never()).save(any(Rental.class));
    }

    @Test
    void testReturnBook_Success() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("testuser@gmail.com");
        when(rentalRepository.findByUserEmailAndBookIdAndStatus("testuser@gmail.com", 1L, RentalStatus.ACTIVE))
                .thenReturn(Optional.of(testRental));
        when(rentalRepository.save(any(Rental.class))).thenReturn(testRental);

        Rental returnedRental = rentalService.returnBook(1L);

        assertNotNull(returnedRental);
        assertEquals(RentalStatus.RETURNED, returnedRental.getRentalStatus());
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }
}