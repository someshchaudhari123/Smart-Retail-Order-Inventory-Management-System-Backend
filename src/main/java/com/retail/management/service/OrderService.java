package com.retail.management.service;

import com.retail.management.dto.OrderRequestDTO;
import com.retail.management.entity.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(OrderRequestDTO request);

    List<Order> getAllOrders();

    void cancelOrder(Long orderId);

    // 🔥 ADD THIS
    Order updateStatus(Long id, String status);

}
