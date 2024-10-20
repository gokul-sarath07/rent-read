package com.crio.rentRead.service;

import com.crio.rentRead.dto.AddBook;
import com.crio.rentRead.entity.Book;

import java.util.List;

public interface BookService {
    Book registerBook(AddBook book);
    Book getBook(Long bookId);
    List<Book> getAllBook();
}
