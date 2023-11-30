package com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo;

import com.Onestop.ecommerce.Entity.orders.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemsRepo extends JpaRepository<OrderItems,Long> {
    Optional<OrderItems> findByIdentifier(String identifier);
}
