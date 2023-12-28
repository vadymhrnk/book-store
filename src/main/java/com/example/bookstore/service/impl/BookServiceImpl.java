package com.example.bookstore.service.impl;

import com.example.bookstore.dto.BookDto;
import com.example.bookstore.dto.BookDtoWithoutCategoryIds;
import com.example.bookstore.dto.CreateBookRequestDto;
import com.example.bookstore.exception.EntityNotFoundException;
import com.example.bookstore.mapper.BookMapper;
import com.example.bookstore.model.Book;
import com.example.bookstore.model.Category;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.repository.CategoryRepository;
import com.example.bookstore.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        List<Category> categories = categoryRepository.findAllById(requestDto.getCategoryIds());
        Book book = bookMapper.toModel(requestDto);
        book.getCategories().addAll(categories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id)
        );
        return bookMapper.toDto(book);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        return bookRepository.findAllByCategoryId(id).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    public BookDto update(Long id, CreateBookRequestDto requestDto) {
        List<Category> categories = categoryRepository.findAllById(requestDto.getCategoryIds());
        Book book = bookMapper.toModel(requestDto);
        book.getCategories().addAll(categories);
        book.setId(id);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
