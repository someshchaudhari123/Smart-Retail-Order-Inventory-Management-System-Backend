package com.retail.management.service;

import com.retail.management.dto.OrderItemDTO;
import com.retail.management.dto.OrderRequestDTO;
import com.retail.management.entity.*;
import com.retail.management.repository.OrderRepository;
import com.retail.management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;

    // 🔥 PLACE ORDER – MAIN BUSINESS LOGIC
    @Override
    @Transactional
    public Order placeOrder(OrderRequestDTO request) {

        // VALIDATION
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order items cannot be empty");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.SHIPPED);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDTO itemDTO : request.getItems()) {

            if (itemDTO.getQuantity() <= 0) {
                throw new RuntimeException("Quantity must be greater than 0");
            }

            Product product = productRepo.findById(itemDTO.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException("Product ID " + itemDTO.getProductId() + " not found"));

            // STOCK CHECK
            if (product.getStockQty() < itemDTO.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            // REDUCE STOCK
            product.setStockQty(product.getStockQty() - itemDTO.getQuantity());
            productRepo.save(product);   // IMPORTANT

            // CREATE ORDER ITEM
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            orderItems.add(orderItem);
        }

        order.setItems(orderItems);

        return orderRepo.save(order); // Cascade saves orderItems
    }

    // VIEW ALL ORDERS
    @Override
    @Transactional(readOnly = true)   // 🔥 THIS FIXES JSON LAZY ERROR
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    // CANCEL ORDER
    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order already cancelled");
        }

        // RESTORE STOCK
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQty(product.getStockQty() + item.getQuantity());
            productRepo.save(product);
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);
    }
    // UPDATE ORDER STATUS
    @Override
    @Transactional
    public Order updateStatus(Long orderId, String status) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        try {
            OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
            order.setStatus(newStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }

        return orderRepo.save(order);
    }

}
