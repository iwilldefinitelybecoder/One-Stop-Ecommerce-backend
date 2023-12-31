package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface productsRepo extends JpaRepository<Product, Long> {
//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.images LEFT JOIN FETCH p.tags WHERE p.id = :productId")
//    Product findProductWithImagesAndTagsById(@Param("productId") Long id);
    Optional<Product> findByIdentifier(String productIdentifier);

    @Query(value = "SELECT * FROM products WHERE name LIKE %:keyword% AND (:category IS NULL OR category = :category)", nativeQuery = true)
    List<Product> findProductsByRegex(@Param("keyword") String keyword, @Param("category") String category);

    List<Product> findAllByCategory(String category);
    List<Product> findAllByVendorId(Long name);
}
