package com.example.e_commerce.repository;

import com.example.e_commerce.entity.ProductStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockHistoryRepository extends JpaRepository<ProductStockHistory, Integer> {
}