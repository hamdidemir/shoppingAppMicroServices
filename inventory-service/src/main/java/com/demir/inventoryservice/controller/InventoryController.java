package com.demir.inventoryservice.controller;

import com.demir.inventoryservice.model.Inventory;
import com.demir.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    public List<Inventory> getAllProducts() {
        return inventoryService.getAllProducts();
    }

    @PostMapping("/update")
    public Inventory updateInventory(@RequestBody Map<String, Object> request) {
        String productId = (String) request.get("productId");
        int quantity = (int) request.get("quantity");
        return inventoryService.updateInventory(productId, quantity);
    }

    @GetMapping("/{productId}")
    public Inventory getInventoryByProductId(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    @PostMapping("/initializeInventory/{productId}")
    public ResponseEntity<Void> initializeInventory(@PathVariable String productId) {
        inventoryService.initializeInventory(productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

