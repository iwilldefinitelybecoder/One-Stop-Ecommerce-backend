package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.MetaAttribute;
import com.Onestop.ecommerce.Entity.products.MetaAttributes;
import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialAttributesRepo extends JpaRepository<MetaAttributes,Long> {



    @Query("SELECT ma FROM MetaAttributes ma JOIN  ma.product p WHERE ma.attributes = :attributes AND ma.isActive = :isActive ORDER BY p.averageRating DESC")
    Page<MetaAttributes> findAllProductByAttributesAndIsActive(
            @Param("attributes") MetaAttribute attributes,
            @Param("isActive") boolean isActive,
            Pageable pageable
    );



    MetaAttributes findByProductId(Long productId);
    MetaAttributes findByProductIdAndAttributes(Long productId, MetaAttributes attributes);
}
