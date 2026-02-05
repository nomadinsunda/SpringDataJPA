package com.intheeast.demo.repository;

import com.intheeast.demo.entity.Product;
import com.intheeast.demo.wrapper.Products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Streamable<Product> findByDescriptionContaining(String description);
    
    Products findAllByDescriptionContaining(String description);
}
