package com.example.bookstore.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Long bookId;
    private int quantity;
}
