package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Dto.productsDto.AddCouponsRequest;
import com.Onestop.ecommerce.Dto.productsDto.CouponsResponse;
import com.Onestop.ecommerce.Repository.products.CouponsRepo;
//import com.Onestop.ecommerce.Repository.products.CouponsUsedRepo;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class CouponsServices implements CouponsService {

    private final CouponsRepo couponsRepo;
    private final CustomerServices customerServices;
//    private final CouponsUsedRepo couponsUsedRepo;


    @Override
    public List<CouponsResponse> getAllCoupons(String email) {
        return null;
    }

    @Override
    public CouponsResponse getCoupon(String couponId) {
        return null;
    }

    @Override
    public String addCoupon(AddCouponsRequest addCouponsRequest) {
        return null;
    }

    @Override
    public String deleteCoupon(String couponId) {
        return null;
    }

    @Override
    public String updateCoupon(AddCouponsRequest addCouponsRequest, String couponId) {
        return null;
    }
}
