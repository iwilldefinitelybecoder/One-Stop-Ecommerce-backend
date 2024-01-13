package com.Onestop.ecommerce.Repository.products;

import com.Onestop.ecommerce.Entity.Cupon.Coupons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponsRepo extends JpaRepository<Coupons, Long>{
    Coupons findByCouponCode(String couponCode);

    @Query("SELECT COUNT(cu) FROM CouponUsed cu JOIN cu.customer c JOIN c.user u WHERE cu.coupons.id = :couponId AND u.email = :email")
    long countByCouponIdAndCustomerEmail(@Param("couponId") Long couponId, @Param("email") String email);

    void deleteByCouponCode(String couponCode);



}
