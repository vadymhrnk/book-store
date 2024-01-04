package com.example.bookstore.service.impl;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;
import com.example.bookstore.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto, Long userId) {
        return null;
    }
}
