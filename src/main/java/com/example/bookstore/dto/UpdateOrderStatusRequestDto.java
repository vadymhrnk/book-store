package com.example.bookstore.dto;

import com.example.bookstore.model.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateOrderStatusRequestDto {
    @NotBlank
    private Order.Status status;
}
