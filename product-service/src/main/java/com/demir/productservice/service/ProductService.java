package com.demir.productservice.service;


import com.demir.productservice.model.Product;
import com.demir.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final WebClient.Builder webClientBuilder;

    public Product createProduct(Product product) {
        // Save the product in the product repository
        Product savedProduct = productRepository.save(product);

        // Make an HTTP POST request to the initializeInventory endpoint of the inventory-service
        webClientBuilder.build().post()
                .uri("http://inventory-service/inventory/initializeInventory/{productId}", savedProduct.getId())
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        return savedProduct;
    }



    public Product updateProduct(Product product) {
        // Check if the product exists
        getProductById(product.getId());

        // Update the product in the product repository
        return productRepository.save(product);
    }

    public Product getProductById(String productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProductById(String productId) {
        // Check if the product exists
        getProductById(productId);

        // Delete the product from the product repository
        productRepository.deleteById(productId);

    }
}




