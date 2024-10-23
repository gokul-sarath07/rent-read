package com.crio.rentRead.service;

import com.crio.rentRead.dto.CreateBook;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.exception.BookNotFoundException;
import com.crio.rentRead.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book registerBook(CreateBook book) {
        log.info("Entered registerBook() method - CreateBook: {}", book);
        Book newBook = createBook(book);

        return bookRepository.save(newBook);
    }

    private Book createBook(CreateBook book) {
        Book newBook = new Book();

        newBook.setAuthor(book.getAuthor());
        newBook.setTitle(book.getTitle());
        newBook.setGenre(book.getGenre());
        if (book.getCopiesAvailable() > 1) {
            newBook.setCopiesAvailable(book.getCopiesAvailable());
        }
        log.debug("Book object created for registerBook() method - book: {}", newBook);

        return newBook;
    }

    @Override
    public Book getBook(Long bookId) {
        log.info("Entered getBook() method - bookId: {}", bookId);
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException("Book with id " + bookId + " not found"));
    }

    @Override
    public List<Book> getAllBook() {
        log.info("Entered getAllBook() method");
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        log.info("Entered save method - book: {}", book);
        if (book == null) {
            log.error("Invalid input - book is null");
            throw new BookNotFoundException("Book cannot be null.");
        }

        return bookRepository.save(book);
    }
}
