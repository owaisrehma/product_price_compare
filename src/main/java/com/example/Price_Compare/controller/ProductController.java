package com.example.Price_Compare.controller;

import com.example.Price_Compare.model.Product;
import com.example.Price_Compare.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(required = false) String q) {
        return productService.fetchProducts(q);
    }

    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam("q") String q) {
        return productService.fetchProducts(q);
    }
}
