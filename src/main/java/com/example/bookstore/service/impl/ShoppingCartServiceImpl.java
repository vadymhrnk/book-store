package com.example.bookstore.service.impl;

import com.example.bookstore.dto.AddCartItemRequestDto;
import com.example.bookstore.dto.ShoppingCartDto;
import com.example.bookstore.dto.UpdateCartItemQuantityRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.service.CartItemService;
import com.example.bookstore.service.ShoppingCartService;
import com.example.bookstore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final UserService userService;
    private final CartItemService cartItemService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final BookRepository bookRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;

    @Override
    @Transactional
    public ShoppingCartDto addBookToCart(AddCartItemRequestDto requestDto, Long userId) {
        Long bookId = requestDto.getBookId();
        Book book = bookRepository.findById(bookId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find book by id: " + bookId)
                );

        ShoppingCart shoppingCartFromDb = getOrCreateShoppingCart(userId);

        CartItem cartItem = new CartItem();
        cartItem.setQuantity(requestDto.getQuantity());
        cartItem.setBook(book);
        cartItem.setShoppingCart(shoppingCartFromDb);
        cartItemRepository.save(cartItem);
        shoppingCartFromDb.getCartItems().add(cartItem);
        return shoppingCartMapper.toDto(shoppingCartFromDb);
    }

    @Override
    public ShoppingCartDto getUserCart(Long userId) {
        ShoppingCart shoppingCartFromDb = getOrCreateShoppingCart(userId);
        return shoppingCartMapper.toDto(shoppingCartFromDb);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateQuantity(
            Long userId,
            UpdateCartItemQuantityRequestDto requestDto,
            Long cartItemId
    ) {
        CartItem cartItem = cartItemService.getCartItemById(cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        ShoppingCart shoppingCartFromDb = getOrCreateShoppingCart(userId);
        return shoppingCartMapper.toDto(shoppingCartFromDb);
    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);
    }

    private ShoppingCart getOrCreateShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart();
                    shoppingCart.setUser(userService.getUserById(userId));
                    return shoppingCartRepository.save(shoppingCart);
                });
    }
}
