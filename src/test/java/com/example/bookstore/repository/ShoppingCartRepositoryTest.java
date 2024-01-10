package com.example.bookstore.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.bookstore.model.ShoppingCart;
import com.example.bookstore.model.User;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShoppingCartRepositoryTest {
    private static User user;
    private static ShoppingCart shoppingCart;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        user.setId(1L);
        user.setEmail("bob@gmail.com");
        user.setPassword("$2a$10$x2JkGITcg4AS8.9KFVi93.xPiq2kZ0a30i1UnzrIQOWlkqWhRccyW");
        user.setFirstName("Bob");
        user.setLastName("Smith");
        user.setShippingAddress("Green palm avenue");
        user.setRoles(Set.of());

        shoppingCart = new ShoppingCart();
        shoppingCart.setId(1L);
        shoppingCart.setUser(user);
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:database/users/add-users-to-users-table.sql",
                    "classpath:database/shopping_carts/add-carts-to-shopping-carts-table.sql"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Sql(
            scripts = {
                    "classpath:database/shopping_carts/delete-carts-from-shopping-carts-table.sql",
                    "classpath:database/users/delete-users-from-users-table.sql"
            },
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
    )
    public void findByUserId_ValidUser_ShouldReturnOptionalShoppingCart() {
        Long userId = 1L;

        Optional<ShoppingCart> result = shoppingCartRepository.findByUserId(userId);

        assertTrue(result.isPresent());
        ShoppingCart actual = result.get();

        ShoppingCart expected = shoppingCart;

        assertTrue(EqualsBuilder.reflectionEquals(expected, actual, "id"));
    }
}
