package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT * FROM products WHERE (:keyword IS NULL OR POSITION(:keyword IN name) > 0) AND (COALESCE(:category, '') = '' OR category = :category)", nativeQuery = true)
    List<Product> findProductsByRegex(@Param("keyword") String keyword, @Param("category") String category);


    @Query(value = "SELECT * FROM products " +
            "WHERE (:keyword IS NULL OR POSITION(:keyword IN name) > 0) " +
            "AND ((:categories IS NULL OR :categories = '{}') OR category IN (:categories)) " +
            "AND (:averageRating IS NULL OR average_rating  >= :averageRating) " +
//            "AND (:priceRange IS NULL OR COALESCE(sale_price, regular_price) BETWEEN :priceRange[0] AND :priceRange[1]) " +
            "ORDER BY average_rating DESC NULLS LAST",
            nativeQuery = true)
    Page<Product> findProductsByRegexPageable(@Param("keyword") String keyword,
                                              @Param("categories") List<String> categories,
                                              @Param("averageRating") Double averageRating,
                                              Pageable pageable);



//    @Query(value = "SELECT * FROM products " +
//            "WHERE (:keyword IS NULL OR POSITION(:keyword IN name) > 0) " +
//            "AND (:categories IS NULL OR :categories = '{}' OR category IN (:categories)) " ,
//            nativeQuery = true)
//    Page<Product> findProductsByRegexPageable(@Param("keyword") String keyword,
//                                              @Param("categories") List<String> categories,
//                                              Pageable pageable);







    List<Product> findAllByCategory(String category);
    List<Product> findAllByVendorId(Long name);
}
