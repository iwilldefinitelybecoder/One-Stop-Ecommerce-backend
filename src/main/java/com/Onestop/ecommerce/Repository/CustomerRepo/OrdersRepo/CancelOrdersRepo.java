package com.Onestop.ecommerce.Repository.CustomerRepo.OrdersRepo;

import com.Onestop.ecommerce.Entity.orders.CancelOrders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CancelOrdersRepo extends JpaRepository<CancelOrders,Long> {
    Optional<CancelOrders> findByIdentifier(String identifier);
}
