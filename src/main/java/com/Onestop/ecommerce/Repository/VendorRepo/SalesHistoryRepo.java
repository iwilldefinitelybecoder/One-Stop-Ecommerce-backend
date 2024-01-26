package com.Onestop.ecommerce.Repository.VendorRepo;

import com.Onestop.ecommerce.Entity.vendor.SalesHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesHistoryRepo extends JpaRepository<SalesHistory, Long> {
    List<SalesHistory> findBySalesDataId(Long id);
    List<SalesHistory> findAllBySalesDataVendorUserEmail(String email);
    List<SalesHistory> findBySalesDataProductIdentifier(String identifier);
    List<SalesHistory> findBySalesDataIdentifier(String identifier);
}
