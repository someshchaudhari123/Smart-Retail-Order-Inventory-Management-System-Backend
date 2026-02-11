package com.retail.management.repository;

import com.retail.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // CATEGORY FILTER
    List<Product> findByCategoryId(Long categoryId);

    // LOW STOCK
    List<Product> findByStockQtyLessThan(int qty);

    // TOP SELLING PRODUCTS
    @Query("""
        SELECT oi.product.id, SUM(oi.quantity) 
        FROM OrderItem oi 
        GROUP BY oi.product.id 
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts();
}
