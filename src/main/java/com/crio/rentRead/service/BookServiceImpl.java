package com.crio.rentRead.service;

import com.crio.rentRead.dto.CreateBook;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.exception.BookNotFoundException;
import com.crio.rentRead.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book registerBook(CreateBook book) {
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

        return newBook;
    }

    @Override
    public Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new BookNotFoundException("Book with id " + bookId + " not found"));
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    @Override
    public Book save(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }

        return bookRepository.save(book);
    }
}
