package com.Onestop.ecommerce.Repository.VendorRepo;

import com.Onestop.ecommerce.Entity.vendor.SalesData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesRepo extends JpaRepository<SalesData, Long> {

    List<SalesData> findByVendorUserEmail(String email);
    SalesData findByProductIdentifier(String identifier);

}
