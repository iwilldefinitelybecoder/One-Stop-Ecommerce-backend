package com.Onestop.ecommerce.Repository.VendorRepo;

import com.Onestop.ecommerce.Entity.vendor.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    Optional<Vendor> findByUserEmail(String email);
}
