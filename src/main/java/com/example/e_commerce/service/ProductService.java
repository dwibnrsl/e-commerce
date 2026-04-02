package com.example.e_commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.e_commerce.dto.ProductStockDTO;
import com.example.e_commerce.entity.Product;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.ProductStockHistoryRepository;
import com.example.e_commerce.entity.ProductStockHistory;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public List<ProductStockDTO> getProductStock() {

    List<Object[]> results = repo.getProductStock();

    return results.stream().map(obj -> new ProductStockDTO(
            (Integer) obj[0],
            (String) obj[1],
            (java.math.BigDecimal) obj[2],
            ((Number) obj[3]).intValue()
    )).toList();
    }

    @Autowired
    private ProductStockHistoryRepository stockRepo;

    public void addStock(Integer productId, Integer qty, Integer userId) {

    ProductStockHistory history = new ProductStockHistory();
    history.setProductId(productId);
    history.setQty(qty);
    history.setUserId(userId);
    history.setChangeType("ADD");
    history.setCreatedAt(java.time.LocalDateTime.now());

    stockRepo.save(history);
    }

    public void reduceStock(Integer productId, Integer qty, Integer userId) {

    int currentStock = getProductStock().stream()
            .filter(p -> p.getProductId().equals(productId))
            .findFirst()
            .map(p -> p.getStock())
            .orElse(0);

    if (currentStock < qty) {
        throw new RuntimeException("Stok tidak cukup!");
    }

    ProductStockHistory history = new ProductStockHistory();
    history.setProductId(productId);
    history.setQty(qty);
    history.setUserId(userId);
    history.setChangeType("REDUCE");
    history.setCreatedAt(java.time.LocalDateTime.now());

    stockRepo.save(history);
    }
}