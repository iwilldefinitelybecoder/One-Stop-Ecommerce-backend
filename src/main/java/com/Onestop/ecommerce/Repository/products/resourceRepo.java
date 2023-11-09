package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.resourceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface resourceRepo extends JpaRepository<resourceDetails, Long> {
}
