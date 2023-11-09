package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productsRepo extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.tags WHERE p.id = :productId")
//    Product findProductWithImagesAndTagsById(@Param("productId") Long id);
}
