package com.intheeast.demo.controller;

import com.intheeast.demo.wrapper.Products;
import com.intheeast.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public Products getProducts(@RequestParam String description) {
        return productService.getProductsByDescription(description);
    }

    @GetMapping("/products/total")
    public String getTotalPrice(@RequestParam String description) {
        Products products = productService.getProductsByDescription(description);
        return "Total Price: " + products.getTotalPrice();
    }
}
