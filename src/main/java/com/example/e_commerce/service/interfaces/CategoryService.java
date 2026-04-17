package com.example.e_commerce.service.interfaces;

import java.util.List;

import com.example.e_commerce.model.dto.response.CategoryResponse;
import com.example.e_commerce.model.entity.Category;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
}