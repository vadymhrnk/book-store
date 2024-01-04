package com.example.bookstore.repository;

import com.example.bookstore.model.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            select o
            from Order o
            left join fetch o.orderItems items
            """)
    List<Order> findByUserId(Long userId);

    @Query("""
            select o
            from Order o
            left join fetch o.orderItems items
            where o.id = :id
            """)
    @Override
    Optional<Order> findById(Long id);

    @Query("""
            select o
            from Order o
            left join fetch o.orderItems items
            where o.user.id = :userId and o.id = :orderId
            """)
    Optional<Order> findByUserIdAndId(Long userId, Long orderId);
}
