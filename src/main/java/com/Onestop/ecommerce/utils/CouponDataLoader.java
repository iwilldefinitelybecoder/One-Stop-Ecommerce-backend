//package com.Onestop.ecommerce.utils;
//
//import com.Onestop.ecommerce.Dto.productsDto.AddCouponsRequest;
//import com.Onestop.ecommerce.Entity.Cupon.CouponType;
//import com.Onestop.ecommerce.Entity.Cupon.CouponUsage;
//import com.Onestop.ecommerce.Entity.Cupon.Coupons;
//import com.Onestop.ecommerce.Service.products.CouponsService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class CouponDataLoader implements CommandLineRunner {
//
//    private final CouponsService couponsService;
//
//    public CouponDataLoader(CouponsService couponsService) {
//        this.couponsService = couponsService;
//    }
//
//    @Override
//    public void run(String... args) {
//        AddCouponsRequest couponRequest1 = AddCouponsRequest.builder()
//                .couponDescription("20% off on Category A")
//                .name("SALE20")
//                .discountPercentage(20.0)
//                .discountAmount(0.0)
//                .couponExpiryDate(new Date(System.currentTimeMillis() + 172800000)) // 48 hours ahead
//                .couponStartDate(new Date())
//                .minimumPurchaseAmount(50.0)
//                .maximumDiscountAmount(100.0)
//                .couponType(CouponType.PERCENTAGE)
//                .couponCategory("Category A")
//                .couponUsageLimit(1)
//                .couponUsage(CouponUsage.SINGLE)
//                .build();
//
//        AddCouponsRequest couponRequest2 = AddCouponsRequest.builder()
//                .couponDescription("10% off on Category B")
//                .name("WEEKEND10")
//                .discountPercentage(10.0)
//                .discountAmount(0.0)
//                .couponExpiryDate(new Date(System.currentTimeMillis() + 345600000)) // 96 hours ahead
//                .couponStartDate(new Date())
//                .minimumPurchaseAmount(100.0)
//                .maximumDiscountAmount(150.0)
//                .couponType(CouponType.PERCENTAGE)
//                .couponCategory("Category B")
//                .couponUsageLimit(2)
//                .couponUsage(CouponUsage.MULTIPLE)
//                .build();
//
//        String coupon1 = couponsService.addCoupon(couponRequest1);
//        String coupon2 = couponsService.addCoupon(couponRequest2);
//
//        System.out.println("Coupons added:");
//        System.out.println("SALE20 ID: " + coupon1);
//        System.out.println("WEEKEND10 ID: " + coupon2);
//    }
//
//}
