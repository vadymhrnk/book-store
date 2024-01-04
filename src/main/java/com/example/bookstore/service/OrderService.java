package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.OrderItemDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;
import com.example.bookstore.dto.UpdateOrderStatusRequestDto;
import java.util.List;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto, Long userId);

    List<OrderDto> getOrderHistory(Long userId);

    OrderDto updateStatus(UpdateOrderStatusRequestDto requestDto, Long id);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId);

    OrderItemDto getSpecificOrderItem(Long userId, Long itemId, Long orderId);
}
