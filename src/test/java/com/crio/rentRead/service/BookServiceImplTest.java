package com.crio.rentRead.service;

import com.crio.rentRead.dto.CreateBook;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.exception.BookNotFoundException;
import com.crio.rentRead.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void testRegisterBook() {
        CreateBook createBook = new CreateBook();
        createBook.setTitle("Spring in Action");
        createBook.setAuthor("Craig Walls");
        createBook.setGenre("Technology");
        createBook.setCopiesAvailable(5);

        Book expectedBook = new Book();
        expectedBook.setBookId(1L);
        expectedBook.setTitle("Spring in Action");
        expectedBook.setAuthor("Craig Walls");
        expectedBook.setGenre("Technology");
        expectedBook.setCopiesAvailable(5);

        when(bookRepository.save(any(Book.class))).thenReturn(expectedBook);

        Book registeredBook = bookService.registerBook(createBook);

        assertNotNull(registeredBook);
        assertEquals("Spring in Action", registeredBook.getTitle());
        assertEquals("Craig Walls", registeredBook.getAuthor());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void testGetBook_Found() {
        Book expectedBook = new Book();
        expectedBook.setBookId(1L);
        expectedBook.setTitle("Spring in Action");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(expectedBook));

        Book foundBook = bookService.getBook(1L);

        assertNotNull(foundBook);
        assertEquals(1L, foundBook.getBookId());
        assertEquals("Spring in Action", foundBook.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBook_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBook(1L);
        });

        assertEquals("Book with id 1 not found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

}