package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.e_commerce.model.entity.Category;
import com.example.e_commerce.model.dto.response.ApiResponse;
import com.example.e_commerce.model.dto.response.CategoryResponse;
import com.example.e_commerce.service.interfaces.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getAllCategories())
        );
    }
}