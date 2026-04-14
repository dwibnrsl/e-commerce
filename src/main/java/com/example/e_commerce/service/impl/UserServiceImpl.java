package com.example.e_commerce.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.e_commerce.model.entity.User;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.service.interfaces.UserService;
import com.example.e_commerce.exception.NotFoundException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> data = repo.findAll();

        if (data.isEmpty()) {
            throw new NotFoundException("User tidak ditemukan");
        }

        return data;
    }
}