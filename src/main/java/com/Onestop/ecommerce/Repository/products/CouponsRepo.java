package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.Cupon.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsRepo extends JpaRepository<Coupons, Long>{
    Coupons findByCouponCode(String couponCode);

}
