package com.Onestop.ecommerce.Repository.LogisticsRepo;

import com.Onestop.ecommerce.Entity.Logistics.OrderShippment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderShipment extends JpaRepository<OrderShippment, Long> {
    List<OrderShippment> findByTrackingNumber(String trackingNumber);
    List<OrderShippment> findAllByOrdersIdentifier(String identifier );
    List<OrderShippment> findByOrdersId(Long id);
}
