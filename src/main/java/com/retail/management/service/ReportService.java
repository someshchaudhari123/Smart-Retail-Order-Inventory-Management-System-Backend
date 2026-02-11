package com.retail.management.service;

import com.retail.management.entity.Product;

import java.util.List;

public interface ReportService {

    List<Product> lowStock();

    void cancelOrder(Long orderId);

    List<Object[]> topSellingProducts();
}
