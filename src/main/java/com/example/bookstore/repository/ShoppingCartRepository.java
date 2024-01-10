package com.example.bookstore.repository;

import com.example.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("""
            select cart
            from ShoppingCart cart
            left join fetch cart.cartItems items
            join fetch cart.user
            where cart.user.id = :userId
            """)
    Optional<ShoppingCart> findByUserId(Long userId);
}
