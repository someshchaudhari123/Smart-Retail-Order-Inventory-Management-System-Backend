package com.retail.management.service;

import com.retail.management.entity.OrderItem;
import com.retail.management.entity.Product;
import com.retail.management.repository.OrderItemRepository;
import com.retail.management.repository.ProductRepository;
import com.retail.management.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepo;
    private final OrderItemRepository orderItemRepo;

    @Override
    public List<Product> lowStock() {
        return reportRepo.findLowStockProducts();
    }

    @Override
    public void cancelOrder(Long orderId) {

        List<OrderItem> items = orderItemRepo.findByOrderId(orderId);

        if (items.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        for (OrderItem item : items) {
            Product p = item.getProduct();
            p.setStockQty(p.getStockQty() + item.getQuantity());
            reportRepo.save(p);
        }
    }

    @Override
    public List<Object[]> topSellingProducts() {
        return reportRepo.findTopSellingProducts();
    }
}
