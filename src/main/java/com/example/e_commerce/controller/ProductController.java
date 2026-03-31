package com.example.e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

import com.example.e_commerce.entity.Product;
import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.dto.ProductStockDTO;
import com.example.e_commerce.entity.ProductStockHistory;
import com.example.e_commerce.dto.StockRequest;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<Product> getProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/stock")
    public List<ProductStockDTO> getProductStock() {
        return service.getProductStock();
    }

    @PostMapping("/{id}/add-stock")
    public String addStock(
            @PathVariable Integer id,
            @RequestBody @jakarta.validation.Valid StockRequest request
    ) {
        service.addStock(id, request.getQty());
        return "Stok berhasil ditambahkan!";
    }

    @PostMapping("/{id}/reduce-stock")
    public String reduceStock(
            @PathVariable Integer id,
            @RequestBody @jakarta.validation.Valid StockRequest request
    ) {
        service.reduceStock(id, request.getQty());
        return "Stok berhasil dikurangi!";
    }
}