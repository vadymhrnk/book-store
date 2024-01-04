package com.example.bookstore.mapper;

import com.example.bookstore.config.MapperConfig;
import com.example.bookstore.dto.OrderItemDto;
import com.example.bookstore.model.CartItem;
import com.example.bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(source = "book.id", target = "bookId")
    OrderItemDto toDto(OrderItem orderItem);

    @Mapping(source = "book.price", target = "price")
    OrderItem toOrderItem(CartItem cartItem);
}
