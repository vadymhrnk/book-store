package com.example.bookstore.service;

import com.example.bookstore.dto.CategoryDto;
import com.example.bookstore.dto.CreateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto save(CreateCategoryRequestDto requestDto);

    CategoryDto update(Long id, CreateCategoryRequestDto requestDto);

    void deleteById(Long id);
}
