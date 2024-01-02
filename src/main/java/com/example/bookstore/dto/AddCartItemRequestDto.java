package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCartItemRequestDto {
    @NotBlank
    private Long bookId;
    @NotBlank
    private int quantity;
}
