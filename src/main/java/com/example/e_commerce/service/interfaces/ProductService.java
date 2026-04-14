package com.example.e_commerce.service.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.example.e_commerce.model.dto.request.CreateProductRequest;
import com.example.e_commerce.model.dto.request.UpdateProductRequest;
import com.example.e_commerce.model.dto.response.ProductHistoryResponse;
import com.example.e_commerce.model.dto.response.ProductResponse;
import com.example.e_commerce.model.dto.response.ProductStockResponse;
import com.example.e_commerce.model.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

    List<ProductResponse> getAllProducts();

    List<ProductHistoryResponse> getProductHistory(Integer productId);
    
    List<ProductStockResponse> getProductStock();

    ProductStockResponse addStock(Integer productId, Integer qty, Integer userId);

    ProductStockResponse reduceStock(Integer productId, Integer qty, Integer userId);

    Page<Product> searchProducts(String keyword, BigDecimal minPrice, BigDecimal maxPrice, Integer categoryId, int page, int size);

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse updateProduct(Integer id, UpdateProductRequest request);
}