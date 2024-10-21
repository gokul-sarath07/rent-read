package com.crio.rentRead.controller;

import com.crio.rentRead.dto.CreateBook;
import com.crio.rentRead.entity.Book;
import com.crio.rentRead.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.crio.rentRead.config.PathConstants.*;

@RestController
@RequestMapping(API_BASE_PATH)
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(REGISTER_BOOK)
    public ResponseEntity<Book> registerBook(@Valid @RequestBody CreateBook createBook) {
        Book book = bookService.registerBook(createBook);

        return ResponseEntity.ok(book);
    }

    @GetMapping(GET_ALL_BOOK)
    public ResponseEntity<List<Book>> getAllBook() {
        List<Book> books = bookService.getAllBook();

        return ResponseEntity.ok(books);
    }

    @GetMapping(GET_A_BOOK)
    public ResponseEntity<Book> getBook(@PathVariable("bookId") Long bookId) {
        Book book = bookService.getBook(bookId);

        return ResponseEntity.ok(book);
    }
}
