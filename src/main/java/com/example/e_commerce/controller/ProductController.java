package com.example.e_commerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import jakarta.validation.Valid;

import com.example.e_commerce.service.ProductService;
import com.example.e_commerce.model.dto.response.ApiResponse;
import com.example.e_commerce.model.dto.response.ProductResponse;
import com.example.e_commerce.model.dto.response.ProductStockResponse;
import com.example.e_commerce.model.dto.request.StockRequest;
import com.example.e_commerce.model.entity.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    // ini constructor injection
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {

        List<ProductResponse> data = service.getAllProducts().stream()
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getPrice()))
                .toList();

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", data)
        );
    }

   @GetMapping("/stock")
    public ResponseEntity<ApiResponse<List<ProductStockResponse>>> getProductStock() {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getProductStock())
        );
    }

    @PostMapping("/{id}/add-stock")
        public ResponseEntity<ApiResponse<ProductStockResponse>> addStock(
                @PathVariable Integer id,
                @RequestBody @Valid StockRequest request
        ) {

        ProductStockResponse data = service.addStock(id, request.getQty(), request.getUserId());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", data)
        );
        }

    @PostMapping("/{id}/reduce-stock")
        public ResponseEntity<ApiResponse<ProductStockResponse>> reduceStock(
                @PathVariable Integer id,
                @RequestBody @Valid StockRequest request
        ) {

        ProductStockResponse data = service.reduceStock(id, request.getQty(), request.getUserId());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", data)
        );
        }
}