package com.Onestop.ecommerce.Dto.productsDto;

import com.Onestop.ecommerce.Entity.Cupon.CouponType;
import com.Onestop.ecommerce.Entity.Cupon.CouponUsage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCouponsRequest {
    private String couponDescription;
    private String name;
    private double discountPercentage;
    private double discountAmount;
    private Date couponExpiryDate;
    private Date couponStartDate;
    private double minimumPurchaseAmount;
    private double maximumDiscountAmount;
    private CouponType couponType;
    private String couponCategory;
    private int couponUsageLimit;
    private CouponUsage couponUsage;
}
