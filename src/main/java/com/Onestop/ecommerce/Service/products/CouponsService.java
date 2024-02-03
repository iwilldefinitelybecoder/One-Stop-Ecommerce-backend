package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Dto.productsDto.AddCouponsRequest;
import com.Onestop.ecommerce.Dto.productsDto.CouponsResponse;

import java.util.HashMap;
import java.util.List;

public interface CouponsService {
    List<CouponsResponse> getAllCoupons(String email);
    CouponsResponse getCoupon(String couponId);
    String addCoupon(AddCouponsRequest addCouponsRequest);
    String deleteCoupon(String couponId);
    String updateCoupon(AddCouponsRequest addCouponsRequest,String couponId);
    Double applyCoupon(String couponId,String email);
    HashMap<String, String> validateCoupon(String couponId, String email);


}
