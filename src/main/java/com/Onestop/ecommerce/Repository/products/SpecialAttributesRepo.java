package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.MetaAttributes;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialAttributesRepo extends JpaRepository<MetaAttributes,Long> {

    List<Product> findAllProductByAttributesAndIsActive(String attributes, boolean isActive);
}
