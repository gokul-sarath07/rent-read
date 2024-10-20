package com.crio.rentRead.dto;

import jakarta.validation.constraints.NotBlank;

public class AddBook {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @NotBlank(message = "Genre is required")
    private String Genre;
}
