package com.retail.management.service;

import com.retail.management.dto.ProductDTO;
import com.retail.management.entity.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(ProductDTO dto);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);
}
