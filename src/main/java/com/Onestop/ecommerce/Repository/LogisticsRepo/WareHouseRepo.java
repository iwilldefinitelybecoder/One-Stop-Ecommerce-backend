package com.Onestop.ecommerce.Repository.LogisticsRepo;

import com.Onestop.ecommerce.Entity.Logistics.ProductInventory;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface WareHouseRepo extends JpaRepository<WareHouse, Long> {
    Optional<WareHouse> findByIdentifier(String identifier);
    Optional<List<WareHouse>> findByWareHouseLocation(String location);
    Optional<WareHouse>findByOrdersIdentifier(String identifier);
}
