package com.example.e_commerce.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.e_commerce.model.entity.Category;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.service.interfaces.CategoryService;
import com.example.e_commerce.exception.NotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repo;

    public CategoryServiceImpl(CategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> data = repo.findAll();

        if (data.isEmpty()) {
            throw new NotFoundException("Category tidak ditemukan");
        }

        return data;
    }
}