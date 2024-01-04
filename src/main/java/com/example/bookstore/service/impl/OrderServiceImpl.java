package com.example.bookstore.service.impl;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.OrderItemDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;
import com.example.bookstore.dto.UpdateOrderStatusRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.OrderItemMapper;
import com.example.bookstore.mapper.OrderMapper;
import com.example.bookstore.model.Order;
import com.example.bookstore.model.OrderItem;
import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import com.example.bookstore.repository.CartItemRepository;
import com.example.bookstore.repository.OrderItemRepository;
import com.example.bookstore.repository.OrderRepository;
import com.example.bookstore.repository.ShoppingCartRepository;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto placeOrder(PlaceOrderRequestDto requestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find user by id: " + userId)
                );

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find cart by user id: " + userId)
                );

        BigDecimal total = shoppingCart.getCartItems().stream()
                .map(
                        cartItem -> cartItem
                                .getBook()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        orderRepository.save(order);

        Set<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(orderItemMapper::toOrderItem)
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toSet());

        order.setOrderItems(orderItems);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto updateStatus(UpdateOrderStatusRequestDto requestDto, Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by id: " + id)
                );
        order.setStatus(requestDto.getStatus());
        orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemDto> getOrderItems(Long userId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by id: " + orderId)
                );
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getSpecificOrderItem(Long userId, Long itemId, Long orderId) {
        Order order = orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by id: " + orderId)
                );
        OrderItem item = order.getOrderItems().stream()
                .filter(orderItem -> orderItem.getId().equals(itemId))
                .findFirst()
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order item by id: " + itemId)
                );
        return orderItemMapper.toDto(item);
    }
}
