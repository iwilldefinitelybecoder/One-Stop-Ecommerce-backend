package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.Cupon.CouponUsed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponsUsedRepo extends JpaRepository<CouponUsed, Long> {
    List<CouponUsed> findByCustomerUserEmail(String email);
    List<CouponUsed> findByCoupons_CouponCode(String couponCode);
    CouponUsed  findByCustomerUserEmailAndCoupons_CouponCode(String email, String couponCode);
}



