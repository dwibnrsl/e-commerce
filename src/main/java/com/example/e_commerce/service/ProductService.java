package com.example.e_commerce.service;

import org.springframework.stereotype.Service;
import java.util.List;

import com.example.e_commerce.model.dto.response.ProductStockResponse;
import com.example.e_commerce.model.entity.Product;
import com.example.e_commerce.model.entity.ProductStockHistory;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.ProductStockHistoryRepository;
import com.example.e_commerce.repository.ProductStockView;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final ProductStockHistoryRepository stockRepo;

    // constructor injection
    public ProductService(ProductRepository repo, ProductStockHistoryRepository stockRepo) {
        this.repo = repo;
        this.stockRepo = stockRepo;
    }

    public List<Product> getAllProducts() {
        List<Product> products = repo.findAll();

        if (products.isEmpty()) {
            throw new RuntimeException("Data produk tidak ditemukan");
        }

        return products;
    }

    public List<ProductStockResponse> getProductStock() {

        List<ProductStockView> results = repo.getProductStock();

        if (results.isEmpty()) {
            throw new RuntimeException("Data stok tidak ditemukan");
        }

        return results.stream()
                .map(p -> new ProductStockResponse(
                        p.getProductId(),
                        p.getProductName(),
                        p.getPrice(),
                        p.getStock()
                ))
                .toList();
    }

    public ProductStockResponse addStock(Integer productId, Integer qty, Integer userId) {

        repo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produk tidak ditemukan"));

        ProductStockHistory history = new ProductStockHistory();
        history.setProductId(productId);
        history.setUserId(userId);
        history.setQty(qty);
        history.setChangeType("ADD");
        history.setCreatedAt(java.time.LocalDateTime.now());

        stockRepo.save(history);

        // ambil data terbaru
        return getProductStock().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gagal mengambil data"));
    }

    public ProductStockResponse reduceStock(Integer productId, Integer qty, Integer userId) {

        int currentStock = getProductStock().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .map(ProductStockResponse::getStock)
                .orElse(0);

        if (currentStock == 0) {
            throw new RuntimeException("Produk tidak ditemukan");
        }

        if (currentStock < qty) {
            throw new RuntimeException("Stok tidak cukup");
        }

        ProductStockHistory history = new ProductStockHistory();
        history.setProductId(productId);
        history.setUserId(userId);
        history.setQty(qty);
        history.setChangeType("REDUCE");
        history.setCreatedAt(java.time.LocalDateTime.now());

        stockRepo.save(history);

        return getProductStock().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gagal mengambil data"));
    }
}