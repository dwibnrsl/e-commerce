package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.e_commerce.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}