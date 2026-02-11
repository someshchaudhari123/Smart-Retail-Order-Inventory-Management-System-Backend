package com.retail.management.controller;

import com.retail.management.dto.OrderRequestDTO;
import com.retail.management.entity.Order;
import com.retail.management.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    private final OrderService orderService;

    // PLACE ORDER
    @PostMapping
    public Order placeOrder(@RequestBody OrderRequestDTO request){
        return orderService.placeOrder(request);
    }

    // VIEW ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    // CANCEL ORDER
    @PutMapping("/{id}/cancel")
    public String cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return "Order Cancelled Successfully";
    }

    // UPDATE STATUS
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam String status){
        return orderService.updateStatus(id, status);
    }
}
