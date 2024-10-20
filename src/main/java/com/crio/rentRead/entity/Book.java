package com.crio.rentRead.entity;

import com.crio.rentRead.constants.BookStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Books")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;

    private String author;

    private String Genre;

    @Enumerated(EnumType.STRING)
    private BookStatus availabilityStatus = BookStatus.AVAILABLE;

    private int copiesAvailable = 1;

    public Book() {}
}
