package com.example.e_commerce.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import com.example.e_commerce.exception.NotFoundException;
import com.example.e_commerce.exception.ConflictException;
import com.example.e_commerce.model.dto.request.CreateProductRequest;
import com.example.e_commerce.model.dto.request.UpdateProductRequest;
import com.example.e_commerce.model.dto.response.PagedResponse;
import com.example.e_commerce.model.dto.response.ProductHistoryResponse;
import com.example.e_commerce.model.dto.response.ProductResponse;
import com.example.e_commerce.model.dto.response.ProductStockResponse;
import com.example.e_commerce.model.entity.Product;
import com.example.e_commerce.model.entity.ProductStockHistory;
import com.example.e_commerce.model.entity.Category;
import com.example.e_commerce.model.entity.User;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.repository.ProductStockHistoryRepository;
import com.example.e_commerce.repository.ProductStockView;
import com.example.e_commerce.repository.UserRepository;
import com.example.e_commerce.service.interfaces.ProductService;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductStockHistoryRepository stockRepo;
    private final UserRepository userRepo;
    private final CategoryRepository categoryRepo;

    public ProductServiceImpl(ProductRepository repo, 
        ProductStockHistoryRepository stockRepo, 
        UserRepository userRepo,
        CategoryRepository categoryRepo) {

        this.repo = repo;
        this.stockRepo = stockRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    // SEARCH
    @Override
    public PagedResponse<ProductResponse> searchProducts(
        String keyword,
        BigDecimal minPrice,
        BigDecimal maxPrice,
        Integer categoryId,
        int page,
        int size) {

    // NORMALISASI INPUT 
    if (keyword != null && keyword.isBlank()) {
        keyword = null;
    }

    // VALIDATION
        if (page < 0) {
            throw new IllegalArgumentException("Page must not be less than 0");
        }

        if (size <= 0 || size > 100) {
            throw new IllegalArgumentException("Size must be between 1 and 100");
        }

        if (minPrice != null && maxPrice != null && minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException("minPrice must not be greater than maxPrice");
        }

        if (categoryId != null) {
        categoryRepo.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> result = repo.searchProducts(
            keyword, minPrice, maxPrice, categoryId, pageable
        );

        List<ProductResponse> items = result.getContent().stream()
            .map(p -> new ProductResponse(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getCategory().getName()
            ))
            .toList();

        return new PagedResponse<>(
                items,
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        );
    }

    // GET PRODUCT
    @Override
    public List<ProductResponse> getAllProducts() {

        List<Product> products = repo.findByDeletedFalse();

        if (products.isEmpty()) {
            throw new NotFoundException("Product not found");
        }

        return products.stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getCategory().getName()
                ))
                .toList();
    }
    
    // CREATE PRODUCT
    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        if (repo.existsByName(request.getName())) {
        throw new ConflictException("Product already exists");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setDeleted(false);
        product.setCreatedAt(java.time.LocalDateTime.now());

        repo.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                category.getName()
        );
    }

    // UPDATE PRODUCT
    @Override
    public ProductResponse updateProduct(Integer id, UpdateProductRequest request) {

        Product product = repo.findById(id)
            .filter(p -> !Boolean.TRUE.equals(p.getDeleted()))
            .orElseThrow(() -> new NotFoundException("Product not found"));

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException("Category not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setCategory(category);
        product.setUpdatedAt(java.time.LocalDateTime.now());

        repo.save(product);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                category.getName()
        );
    }

    // DELETE
    @Override
    public void deleteProduct(Integer id) {

        Product product = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setDeleted(true);
        repo.save(product);
    }
    
    // HISTORY
    @Override
    public List<ProductHistoryResponse> getProductHistory(Integer productId) {

        List<ProductStockHistory> histories = stockRepo.findByProduct_Id(productId);

        if (histories.isEmpty()) {
            throw new NotFoundException("History not found");
        }

        return histories.stream()
                .map(h -> new ProductHistoryResponse(
                        h.getProduct().getId(),
                        h.getProduct().getName(),
                        h.getQty(),
                        h.getChangeType(),
                        h.getUser().getName(),
                        h.getCreatedAt()
                ))
                .toList();
    }

    // GET STOCK
    @Override
    public List<ProductStockResponse> getProductStock() {

        List<ProductStockView> results = repo.getProductStock();

        if (results.isEmpty()) {
            throw new NotFoundException("No products found");
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

    // ADD STOCK
    @Override
    public ProductStockResponse addStock(Integer productId, Integer qty, Integer userId) {

        Product product = repo.findById(productId)
        .orElseThrow(() -> new NotFoundException("Product not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ProductStockHistory history = new ProductStockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setQty(qty);
        history.setChangeType("ADD");
        history.setCreatedAt(java.time.LocalDateTime.now());

        stockRepo.save(history);

        return getUpdatedStock(productId);
    }

    //  REDUCE STOCK 
    @Override
    public ProductStockResponse reduceStock(Integer productId, Integer qty, Integer userId) {

        ProductStockResponse current = getProductStock().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (current.getStock() < qty) {
            throw new ConflictException("Stock is not sufficient");
        }

        Product product = repo.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ProductStockHistory history = new ProductStockHistory();
        history.setProduct(product);
        history.setUser(user);
        history.setQty(qty);
        history.setChangeType("REDUCE");
        history.setCreatedAt(java.time.LocalDateTime.now());

        stockRepo.save(history);

        return getUpdatedStock(productId);
    }

    //  HELPER 
    private ProductStockResponse getUpdatedStock(Integer productId) {
        return getProductStock().stream()
                .filter(p -> p.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }
}