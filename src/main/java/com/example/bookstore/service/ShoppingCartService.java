package com.example.bookstore.service;

import com.example.bookstore.dto.AddCartItemRequestDto;
import com.example.bookstore.dto.ShoppingCartDto;
import com.example.bookstore.dto.UpdateCartItemQuantityRequestDto;

public interface ShoppingCartService {
    ShoppingCartDto addBookToCart(AddCartItemRequestDto requestDto, Long userId);

    ShoppingCartDto getUserCart(Long userId);

    ShoppingCartDto updateQuantity(
            Long userId,
            UpdateCartItemQuantityRequestDto requestDto,
            Long cartItemId
    );

    void deleteById(Long id);
}
