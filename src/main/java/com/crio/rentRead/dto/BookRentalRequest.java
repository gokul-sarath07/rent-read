package com.crio.rentRead.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookRentalRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotNull(message = "bookId is required")
    private Long bookId;

    public BookRentalRequest() {}
}
