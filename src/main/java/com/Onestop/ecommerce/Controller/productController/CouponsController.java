package com.Onestop.ecommerce.Controller.productController;

import com.Onestop.ecommerce.Dto.productsDto.AddCouponsRequest;
import com.Onestop.ecommerce.Service.products.CouponsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/coupons")
public class CouponsController {

    private final CouponsServices couponsServices;

    @PostMapping("/add")
    public ResponseEntity<String> addCoupon(@RequestBody AddCouponsRequest addCouponsRequest) {
        try {
            String response = couponsServices.addCoupon(addCouponsRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteCoupon(@RequestParam String couponId) {
        try {
            String response = couponsServices.deleteCoupon(couponId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCoupon() {
        try {
            var userName = SecurityContextHolder.getContext().getAuthentication().getName();
            return ResponseEntity.status(HttpStatus.OK).body(couponsServices.getAllCoupons(userName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<String> getCoupon(@RequestParam String couponId) {
        try {
            // Modify to return actual data using CouponsResponse
            return new ResponseEntity<>("Get coupon with ID: " + couponId, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateCoupon(@RequestBody AddCouponsRequest addCouponsRequest,
                                               @RequestParam String couponId) {
        try {
            String response = couponsServices.updateCoupon(addCouponsRequest, couponId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<String> activateCoupon() {
        try {
            // Logic to activate coupon
            return new ResponseEntity<>("Coupon activated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error activating coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("/applyCoupon")
//    public ResponseEntity<String> applyCoupon(@RequestParam String couponId, @RequestParam String email) {
//        try {
//            String response = couponsServices.applyCoupon(couponId, email);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error applying coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/validateCoupon")
    public ResponseEntity<?> validateCoupon(@RequestParam String couponId,
                                                                  @RequestParam String email) {
        try {
            HashMap<String, String> response = couponsServices.validateCoupon(couponId, email);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error validating coupon: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
