package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto, Long userId);
}
