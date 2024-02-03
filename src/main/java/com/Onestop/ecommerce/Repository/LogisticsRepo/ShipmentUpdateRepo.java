package com.Onestop.ecommerce.Repository.LogisticsRepo;

import com.Onestop.ecommerce.Entity.Logistics.ShipmentUpdates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentUpdateRepo extends JpaRepository<ShipmentUpdates, Long> {

    List<ShipmentUpdates> findAllByTrackingNumber(String trackingNumber);

}
