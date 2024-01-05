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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

        BigDecimal total = calculateTotal(shoppingCart);

        Order order = createOrder(user, total, requestDto);

        Set<OrderItem> orderItems = orderItemMapper.toOrderItemSet(shoppingCart.getCartItems());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));

        order.setOrderItems(orderItems);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(shoppingCart.getCartItems());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getOrderHistory(Long userId, Pageable pageable) {
        List<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orderMapper.toDtoList(orders);
    }

    @Override
    @Transactional
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
    public List<OrderItemDto> getOrderItems(Long userId, Long orderId, Pageable pageable) {
        orderRepository.findByUserIdAndId(userId, orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order by id: " + orderId)
                );
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId, pageable);
        return orderItemMapper.toDtoList(orderItems);
    }

    @Override
    public OrderItemDto getSpecificOrderItem(Long userId, Long itemId, Long orderId) {
        OrderItem orderItem = orderItemRepository.findByIdAndOrderId(itemId, orderId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find order item by id: " + itemId)
                );
        return orderItemMapper.toDto(orderItem);
    }

    private BigDecimal calculateTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(
                        cartItem -> cartItem
                                .getBook()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createOrder(User user, BigDecimal total, PlaceOrderRequestDto requestDto) {
        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setTotal(total);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());
        return order;
    }
}
