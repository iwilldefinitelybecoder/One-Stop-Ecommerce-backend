package com.Onestop.ecommerce.Repository.LogisticsRepo;

import com.Onestop.ecommerce.Entity.Logistics.OrderShippment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderShipment extends JpaRepository<OrderShippment, Long> {
    OrderShippment findByTrackingNumber(String trackingNumber);
    OrderShippment findByOrderItemsIdentifier(String identifier );
    OrderShippment findByOrderItemsId(Long id);
}
