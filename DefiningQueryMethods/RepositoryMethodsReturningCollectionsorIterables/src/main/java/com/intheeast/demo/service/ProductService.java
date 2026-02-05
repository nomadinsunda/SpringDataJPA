package com.intheeast.demo.service;

import com.intheeast.demo.wrapper.Products;
import com.intheeast.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Products getProductsByDescription(String description) {
        return productRepository.findAllByDescriptionContaining(description);
    }

    public void printTotalPrice(String description) {
        Products products = getProductsByDescription(description);
        
        System.out.println("Total Price: " + products.getTotalPrice());
    }
}