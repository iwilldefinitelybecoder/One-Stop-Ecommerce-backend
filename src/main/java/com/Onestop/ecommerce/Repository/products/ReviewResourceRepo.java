package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.products.ReviewImageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReviewResourceRepo extends JpaRepository<ReviewImageResource,Long> {

    List<ReviewImageResource> findAllByReviewId(Long id);
}
