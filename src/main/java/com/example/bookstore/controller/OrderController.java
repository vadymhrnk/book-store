package com.example.bookstore.controller;

import com.example.bookstore.dto.OrderDto;
import com.example.bookstore.dto.OrderItemDto;
import com.example.bookstore.dto.PlaceOrderRequestDto;
import com.example.bookstore.dto.UpdateOrderStatusRequestDto;
import com.example.bookstore.model.User;
import com.example.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoint for managing orders")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Place an order", description = "Creates an order")
    public OrderDto placeOrder(
            @RequestBody @Valid PlaceOrderRequestDto requestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.placeOrder(requestDto, user.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    @Operation(summary = "Get user's order history", description = "Get user's order history")
    public List<OrderDto> getOrderHistory(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderHistory(user.getId(), pageable);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    @Operation(summary = "Update order status by id", description = "Update order status by id")
    public OrderDto updateStatus(
            @RequestBody @Valid UpdateOrderStatusRequestDto requestDto,
            @PathVariable Long id
    ) {
        return orderService.updateStatus(requestDto, id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{order-id}/items")
    @Operation(
            summary = "Get all items from order by id",
            description = "Get all items from order by id"
    )
    public List<OrderItemDto> getOrderItems(
            Authentication authentication,
            @PathVariable(name = "order-id") Long orderId,
            Pageable pageable
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItems(user.getId(), orderId, pageable);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{order-id}/items/{item-id}")
    @Operation(
            summary = "Get a specific item within an order",
            description = "Get a specific item within an order"
    )
    public OrderItemDto getSpecificOrderItem(
            Authentication authentication,
            @PathVariable(name = "order-id") Long orderId,
            @PathVariable(name = "item-id") Long itemId
    ) {
        User user = (User) authentication.getPrincipal();
        return orderService.getSpecificOrderItem(user.getId(), itemId, orderId);
    }
}
