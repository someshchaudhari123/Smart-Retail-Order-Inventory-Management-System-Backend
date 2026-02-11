package com.retail.management.repository;

import com.retail.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Product, Long> {

    // LOW STOCK
    @Query("SELECT p FROM Product p WHERE p.stockQty < 5")
    List<Product> findLowStockProducts();

    // TOP SELLING PRODUCTS
    @Query("""
        SELECT oi.product.id, SUM(oi.quantity)
        FROM OrderItem oi
        GROUP BY oi.product.id
        ORDER BY SUM(oi.quantity) DESC
    """)
    List<Object[]> findTopSellingProducts();
}
