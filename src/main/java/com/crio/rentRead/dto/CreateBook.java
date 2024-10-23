package com.crio.rentRead.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBook {
    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "author is required")
    private String author;

    @NotBlank(message = "genre is required")
    private String genre;

    private int copiesAvailable;

    public CreateBook() {}
}
