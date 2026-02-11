package com.retail.management.controller;

import com.retail.management.dto.ProductDTO;
import com.retail.management.entity.Product;
import com.retail.management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // ADD PRODUCT
    @PostMapping
    public Product add(@RequestBody ProductDTO dto) {
        return productService.addProduct(dto);
    }

    // GET ALL
    @GetMapping
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return productService.updateProduct(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product Deleted Successfully";
    }
}
