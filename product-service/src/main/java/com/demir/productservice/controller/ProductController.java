package com.demir.productservice.controller;

import com.demir.productservice.model.Product;
import com.demir.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return productService.getProductById(productId);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Transactional
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProductById(@PathVariable String productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

}
