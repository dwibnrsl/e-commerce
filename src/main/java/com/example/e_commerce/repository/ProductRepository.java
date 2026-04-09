package com.example.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.e_commerce.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
@Query(value = """
        SELECT 
            p.id AS productId,
            p.name AS productName,
            p.price,
            COALESCE(SUM(
                CASE 
                    WHEN psh.change_type = 'ADD' THEN psh.qty
                    WHEN psh.change_type = 'REDUCE' THEN -psh.qty
                    ELSE 0
                END
            ), 0) AS stock
        FROM product p
        LEFT JOIN product_stock_history psh ON p.id = psh.product_id
        WHERE p.deleted = false
        GROUP BY p.id, p.name, p.price
        ORDER BY p.id ASC
    """, nativeQuery = true)
    List<ProductStockView> getProductStock();

}
