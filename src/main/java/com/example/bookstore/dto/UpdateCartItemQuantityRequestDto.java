package com.example.bookstore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateCartItemQuantityRequestDto {
    @Positive
    @NotNull
    private int quantity;
}
