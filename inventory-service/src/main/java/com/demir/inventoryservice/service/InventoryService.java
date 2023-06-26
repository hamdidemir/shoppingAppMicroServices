package com.demir.inventoryservice.service;

import com.demir.inventoryservice.model.Inventory;
import com.demir.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;


    public Inventory updateInventory(String productId, int quantity) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByProductId(productId);
        Inventory inventory;

        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
        } else {
            inventory = Inventory.builder()
                    .productId(productId)
                    .quantity(quantity)
                    .build();
        }

        return inventoryRepository.save(inventory);
    }

    public Inventory getInventoryByProductId(String productId) {
        return inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }

    public void initializeInventory(String productId) {
        // Create a new inventory entry for the product
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setQuantity(0); // Set the initial quantity to 0

        // Save the inventory entry in the repository
        inventoryRepository.save(inventory);
    }


    public void deleteInventoryByProductId(String productId) {
        // Find and delete the inventory entry by product ID
        inventoryRepository.deleteByProductId(productId);
    }

    public List<Inventory> getAllProducts() {
        return inventoryRepository.findAll();
    }
}

