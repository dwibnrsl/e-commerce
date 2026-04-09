package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.e_commerce.model.entity.ProductStockHistory;

public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Integer> {
}