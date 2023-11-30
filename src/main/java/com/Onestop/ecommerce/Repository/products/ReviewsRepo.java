package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepo extends JpaRepository<Review,Long> {
    List<Review> findAllByProductIdentifier(String productId);
}
