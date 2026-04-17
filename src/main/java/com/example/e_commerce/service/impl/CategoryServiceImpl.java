package com.example.e_commerce.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.e_commerce.exception.NotFoundException;
import com.example.e_commerce.model.dto.response.CategoryResponse;
import com.example.e_commerce.model.entity.Category;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.service.interfaces.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryServiceImpl(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        List<Category> categories = categoryRepo.findAll();

        if (categories.isEmpty()) {
            throw new NotFoundException("Category not found");
        }

        return categories.stream()
                .map(c -> new CategoryResponse(
                        c.getId(),
                        c.getName()
                ))
                .toList();
    }
}