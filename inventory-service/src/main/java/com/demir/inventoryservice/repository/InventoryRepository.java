package com.demir.inventoryservice.repository;

import com.demir.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(String productId);

    @Modifying
    @Query("DELETE FROM Inventory i WHERE i.productId = :productId")
    void deleteByProductId(@Param("productId") String productId);
}
