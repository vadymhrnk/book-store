package com.example.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaceOrderRequestDto {
    @NotBlank
    private String shippingAddress;
}
