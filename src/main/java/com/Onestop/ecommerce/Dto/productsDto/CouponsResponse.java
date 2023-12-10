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
public class CouponsResponse {
    private String couponCode;
    private String couponDescription;
    private Date couponExpiryDate;
    private Date couponStartDate;
    private String name;
    private double discountPercentage;
    private double discountAmount;
    private double minimumPurchaseAmount;
    private double maximumDiscountAmount;
}
