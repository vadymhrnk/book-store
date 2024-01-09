package com.example.bookstore.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.bookstore.dto.ShoppingCartDto;
import com.example.bookstore.model.Role;
import com.example.bookstore.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShoppingCartControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setId(1L);
        role.setName(Role.RoleName.USER);

        User user = new User();
        user.setId(1L);
        user.setEmail("bob@gmail.com");
        user.setPassword("$2a$10$x2JkGITcg4AS8.9KFVi93.xPiq2kZ0a30i1UnzrIQOWlkqWhRccyW");
        user.setRoles(Set.of(role));

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, user.getPassword(), user.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @WithMockUser(username = "user", roles = "USER")
    @Test
    public void getCart_ValidCart_Successful() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/api/cart").contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        ShoppingCartDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                ShoppingCartDto.class
        );

        Assertions.assertEquals(1, actual.getId());
    }
}
