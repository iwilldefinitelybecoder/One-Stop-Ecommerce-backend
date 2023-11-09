package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.productTypeTags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tagsRepo extends JpaRepository<productTypeTags, Long> {
}
