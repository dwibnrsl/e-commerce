package com.example.e_commerce.service.impl;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.e_commerce.exception.NotFoundException;
import com.example.e_commerce.model.dto.response.UserResponse;
import com.example.e_commerce.model.entity.User;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users = userRepo.findAll();

        if (users.isEmpty()) {
            throw new NotFoundException("User not found");
        }

        return users.stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getName()
                ))
                .toList();
    }
}