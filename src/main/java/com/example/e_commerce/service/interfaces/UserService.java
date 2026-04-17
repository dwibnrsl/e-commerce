package com.example.e_commerce.service.interfaces;

import java.util.List;

import com.example.e_commerce.model.dto.response.UserResponse;
import com.example.e_commerce.model.entity.User;

public interface UserService {
    List<UserResponse> getAllUsers();
}