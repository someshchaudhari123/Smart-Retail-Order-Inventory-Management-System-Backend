package com.retail.management.service;

import com.retail.management.dto.ProductDTO;
import com.retail.management.entity.Category;
import com.retail.management.entity.Product;
import com.retail.management.repository.CategoryRepository;
import com.retail.management.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Override
    public Product addProduct(ProductDTO dto) {

        if (dto.getCategoryId() == null) {
            throw new RuntimeException("Category ID is required");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category Not Found"));

        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setStockQty(dto.getStockQty());
        p.setCategory(category);

        return productRepo.save(p);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO dto) {

        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));

        existing.setName(dto.getName());
        existing.setPrice(dto.getPrice());
        existing.setStockQty(dto.getStockQty());

        if (dto.getCategoryId() != null) {
            Category category = categoryRepo.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category Not Found"));
            existing.setCategory(category);
        }

        return productRepo.save(existing);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
