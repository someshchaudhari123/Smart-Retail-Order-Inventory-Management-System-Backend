package com.retail.management.controller;

import com.retail.management.entity.Product;
import com.retail.management.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService service;

    // LOW STOCK
    @GetMapping("/low-stock")
    public List<Product> lowStock(){
        return service.lowStock();
    }

    // CANCEL ORDER
    @PostMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id){
        service.cancelOrder(id);
        return "Order Cancelled & Stock Restored";
    }

    // TOP SELLING
    @GetMapping("/top-selling")
    public List<Object[]> topSelling(){
        return service.topSellingProducts();
    }
}
