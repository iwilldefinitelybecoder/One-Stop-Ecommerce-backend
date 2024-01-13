package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.resourceDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface resourceRepo extends JpaRepository<resourceDetails, Long> {

    Optional<List<resourceDetails>> findByProductId(Long productId);
}
