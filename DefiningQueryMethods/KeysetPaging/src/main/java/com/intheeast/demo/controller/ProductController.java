package com.intheeast.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intheeast.demo.domain.Product;
import com.intheeast.demo.dto.ScrollResponse;
import com.intheeast.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // 추가: final 필드용 생성자를 자동으로 만듦
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    
    // http://localhost:8080/api/products/offset?offset=0
    @GetMapping("/offset")
    public ResponseEntity<ScrollResponse> getProductList(
            @RequestParam(value = "offset", defaultValue = "0") long offset) {
        
        ScrollResponse response = productService.getProductsByOffset(offset);
        return ResponseEntity.ok(response);
    }

    // /api/products
    // /api/products?cursor=?...
    @GetMapping
    public ResponseEntity<ScrollResponse> list(@RequestParam(required = false) String cursor) {
        return ResponseEntity.ok(productService.getProducts(cursor));
    }
}

