package com.example.bookstore.service;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.OrderItemDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;
import com.example.bookstore.dto.UpdateOrderStatusRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto placeOrder(PlaceOrderRequestDto requestDto, Long userId);

    List<OrderDto> getOrderHistory(Long userId, Pageable pageable);

    OrderDto updateStatus(UpdateOrderStatusRequestDto requestDto, Long id);

    List<OrderItemDto> getOrderItems(Long userId, Long orderId, Pageable pageable);

    OrderItemDto getSpecificOrderItem(Long userId, Long itemId, Long orderId);
}
