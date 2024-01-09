package com.example.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bookstore.model.ShoppingCart;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @Sql(
            scripts = {
                    "classpath:database/users/add-users-to-users-table.sql",
                    "classpath:database/shopping_carts/add-carts-to-shopping-carts-table.sql",
                    "classpath:database/books/add-books-to-books-table.sql",
                    "classpath:database/cart_items/add-items-to-cart-items-table.sql"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = {
                    "classpath:database/cart_items/delete-items-from-cart-items-table.sql",
                    "classpath:database/books/delete-books-from-books-table.sql",
                    "classpath:database/shopping_carts/delete-carts-from-shopping-carts-table.sql",
                    "classpath:database/users/delete-users-from-users-table.sql"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void findByUserId_ValidUser_ShouldReturnOptionalShoppingCart() {
        Long userId = 1L;

        Optional<ShoppingCart> result = shoppingCartRepository.findByUserId(userId);

        assertTrue(result.isPresent());
        ShoppingCart shoppingCart = result.get();

        assertNotNull(shoppingCart);
        assertEquals(userId, shoppingCart.getUser().getId());
    }
}
