package com.Onestop.ecommerce.Controller.productController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/coupons")
public class CouponsController {

    @PostMapping("/add")
    public String addCoupon(){
        return "add coupon";
    }

    @PostMapping("/delete")
    public String deleteCoupon(){
        return "delete coupon";
    }

    @GetMapping("/getAll")
    public String getAllCoupon(){
        return "get all coupon";
    }

    @GetMapping("/get")
    public String getCoupon(){
        return "get coupon";
    }

    @PostMapping("/update")
    public String updateCoupon(){
        return "update coupon";
    }
    @PostMapping("/applyCoupon")
    public String applyCoupon(){
        return "apply coupon";
    }



}
