package com.Onestop.ecommerce.Repository.LogisticsRepo;

import com.Onestop.ecommerce.Entity.Logistics.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<ProductInventory, Long> {
    Optional<ProductInventory> findByProductIdentifier(String productId);
    Optional<ProductInventory> findByIdentifier(String identifier);
}
