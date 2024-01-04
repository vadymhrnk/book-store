package com.example.bookstore.service.impl;

import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find cart item by id: " + id)
                );
    }
}
