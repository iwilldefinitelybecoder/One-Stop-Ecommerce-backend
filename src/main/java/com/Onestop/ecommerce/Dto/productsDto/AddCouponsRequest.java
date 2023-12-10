package com.Onestop.ecommerce.Dto.productsDto;

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
    private double minimumPurchaseAmount;
    private double maximumDiscountAmount;
    private String couponType;
    private String couponCategory;
    private String couponUsageLimit;
}
