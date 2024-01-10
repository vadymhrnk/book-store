package com.example.bookstore.service.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.example.bookstore.dto.AddCartItemRequestDto;
import com.example.bookstore.dto.CartItemDto;
import com.example.bookstore.dto.ShoppingCartDto;
import com.example.bookstore.mapper.ShoppingCartMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceImplTest {
    private static Book book;
    private static User user;
    private static ShoppingCart shoppingCart;
    private static CartItemDto cartItemDto;
    private static AddCartItemRequestDto requestDto;
    private static CartItem cartItem;
    private static ShoppingCartDto shoppingCartDto;
    private static Long bookId;
    private static int quantity;
    private static Long userId;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

    @BeforeAll
    static void beforeAll() {
        userId = 1L;
        bookId = 2L;
        quantity = 3;
        requestDto = new AddCartItemRequestDto();
        requestDto.setBookId(bookId);
        requestDto.setQuantity(quantity);

        book = new Book();
        book.setId(1L);
        book.setTitle("Java 8");
        book.setAuthor("Bob Smith");
        book.setIsbn("9781234567897");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setDescription("Description of Java");
        book.setCoverImage("JavaBook.jpg");

        user = new User();
        user.setId(1L);
        user.setEmail("bob@gmail.com");
        user.setPassword("$2a$10$x2JkGITcg4AS8.9KFVi93.xPiq2kZ0a30i1UnzrIQOWlkqWhRccyW");
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setShippingAddress("Green palm avenue");

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);

        cartItemDto = new CartItemDto();
        cartItemDto.setId(1L);
        cartItemDto.setBookId(bookId);
        cartItemDto.setBookTitle(book.getTitle());
        cartItemDto.setQuantity(quantity);

        cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.setBook(book);
        cartItem.setQuantity(quantity);

        shoppingCart.setCartItems(new HashSet<>(Set.of(cartItem)));

        shoppingCartDto = new ShoppingCartDto();
        shoppingCartDto.setId(1L);
        shoppingCartDto.setUserId(userId);
        shoppingCartDto.setCartItems(Set.of(cartItemDto));

    }

    @Test
    public void addBookToCart_ValidBook_Successful() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(shoppingCartRepository.findByUserId(userId)).thenReturn(Optional.of(shoppingCart));
        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartDto);

        ShoppingCartDto expected = shoppingCartDto;

        ShoppingCartDto actual = shoppingCartService.addBookToCart(requestDto, userId);

        assertNotNull(actual);
        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }
}
