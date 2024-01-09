package com.example.bookstore.service.impl;

import static org.mockito.Mockito.when;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void save_ValidBook_Successful() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Java 8");
        requestDto.setAuthor("Bob Smith");
        requestDto.setIsbn("9781234567897");
        requestDto.setPrice(BigDecimal.valueOf(29.99));
        requestDto.setDescription("Description of Java");
        requestDto.setCoverImage("JavaBook.jpg");
        requestDto.setCategoryIds(Set.of(1L));

        Category category = new Category();
        category.setId(1L);
        category.setName("Fiction");
        category.setDescription("Fiction books");

        List<Category> categories = List.of(category);

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Java 8");
        book.setAuthor("Bob Smith");
        book.setIsbn("9781234567897");
        book.setPrice(BigDecimal.valueOf(29.99));
        book.setDescription("Description of Java");
        book.setCoverImage("JavaBook.jpg");
        book.setCategories(new HashSet<>(Set.of(categories.get(0))));

        BookDto expectedDto = new BookDto();
        expectedDto.setId(1L);
        expectedDto.setTitle("Java 8");
        expectedDto.setAuthor("Bob Smith");
        expectedDto.setIsbn("9781234567897");
        expectedDto.setPrice(BigDecimal.valueOf(29.99));
        expectedDto.setDescription("Description of Java");
        expectedDto.setCoverImage("JavaBook.jpg");
        expectedDto.setCategoryIds(Set.of(1L));

        when(categoryRepository.findAllById(requestDto.getCategoryIds())).thenReturn(categories);
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expectedDto);

        BookDto actualDto = bookService.save(requestDto);

        EqualsBuilder.reflectionEquals(expectedDto, actualDto);
    }
}
