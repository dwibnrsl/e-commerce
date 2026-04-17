package com.example.e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

import com.example.e_commerce.model.dto.request.CreateProductRequest;
import com.example.e_commerce.model.dto.request.StockRequest;
import com.example.e_commerce.model.dto.request.UpdateProductRequest;
import com.example.e_commerce.model.dto.response.ApiResponse;
import com.example.e_commerce.model.dto.response.PagedResponse;
import com.example.e_commerce.model.dto.response.ProductHistoryResponse;
import com.example.e_commerce.model.dto.response.ProductResponse;
import com.example.e_commerce.model.dto.response.ProductStockResponse;
import com.example.e_commerce.model.entity.Product;
import com.example.e_commerce.service.interfaces.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    // constructor injection
    public ProductController(ProductService service) {
        this.service = service;
    }

    // SEARCH
    @GetMapping("/search")
        public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> searchProducts(
                @RequestParam(required = false) String keyword,
                @RequestParam(required = false) BigDecimal minPrice,
                @RequestParam(required = false) BigDecimal maxPrice,
                @RequestParam(required = false) Integer categoryId,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size
        ) {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success",
                        service.searchProducts(keyword, minPrice, maxPrice, categoryId, page, size))
        );
        }

    // GET ALL PRODUCT
    @GetMapping
        public ResponseEntity<ApiResponse<List<ProductResponse>>> getProducts() {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getAllProducts())
        );
        }

       // CREATE PRODUCT
        @PostMapping
        public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
                @RequestBody @Valid CreateProductRequest request
        ) {

        ProductResponse data = service.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Product created successfully", data));
        }

        // UPDATE PRODUCT
        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
                @PathVariable Integer id,
                @RequestBody @Valid UpdateProductRequest request
        ) {

        ProductResponse data = service.updateProduct(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Product updated successfully", data)
        );
        }

        // DELETE PRODUCT
        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable Integer id) {

        service.deleteProduct(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Product deleted successfully", null)
        );
        }

    // GET PRODUCT STOCK
    @GetMapping("/stock")
    public ResponseEntity<ApiResponse<List<ProductStockResponse>>> getProductStock() {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getProductStock())
        );
    }

    // HISTORY
    @GetMapping("/{id}/history")
        public ResponseEntity<ApiResponse<List<ProductHistoryResponse>>> getProductHistory(
                @PathVariable Integer id
        ) {

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Success", service.getProductHistory(id))
        );
        }

    // ADD STOCK
    @PostMapping("/{id}/add-stock")
    public ResponseEntity<ApiResponse<ProductStockResponse>> addStock(
            @PathVariable Integer id,
            @RequestBody @Valid StockRequest request
    ) {

        ProductStockResponse data = service.addStock(id, request.getQty(), request.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, "Stock added successfully", data));
    }

    // REDUCE STOCK
    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<ApiResponse<ProductStockResponse>> reduceStock(
            @PathVariable Integer id,
            @RequestBody @Valid StockRequest request
    ) {

        ProductStockResponse data = service.reduceStock(id, request.getQty(), request.getUserId());

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Stock reduced successfully", data)
        );
    }
}