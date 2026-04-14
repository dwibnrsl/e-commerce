package com.example.e_commerce.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.e_commerce.model.entity.User;
import com.example.e_commerce.model.dto.response.ApiResponse;
import com.example.e_commerce.service.interfaces.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAll() {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getAllUsers())
        );
    }
}