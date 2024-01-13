package com.Onestop.ecommerce.Service.products;

import com.Onestop.ecommerce.Dto.productsDto.AddCouponsRequest;
import com.Onestop.ecommerce.Dto.productsDto.CouponsResponse;
import com.Onestop.ecommerce.Entity.Cupon.CouponUsage;
import com.Onestop.ecommerce.Entity.Cupon.CouponUsed;
import com.Onestop.ecommerce.Entity.Cupon.Coupons;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.products.CouponsRepo;
//import com.Onestop.ecommerce.Repository.products.CouponsUsedRepo;
import com.Onestop.ecommerce.Repository.products.CouponsUsedRepo;
import com.Onestop.ecommerce.Service.Customer.CartServices;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponsServices implements CouponsService {

    private final CouponsRepo couponsRepo;
    private final CustomerServices customerServices;
    private final CouponsUsedRepo couponsUsedRepo;
    private final CartRepo cartRepo;
    private final CartServices cart;


    @Override
    public List<CouponsResponse> getAllCoupons(String email) {
        var coupons = couponsRepo.findAll();
        List<CouponsResponse> availableCoupons = new ArrayList<>();
        var cartTotal = cart.getCartTotal(email);

        for (Coupons coupon : coupons) {
            if (!coupon.isCouponValid() || !coupon.getCouponActive()) {
                continue; // Skip invalid or inactive coupons
            }

            if (coupon.getCouponUsage().equals(CouponUsage.SINGLE)) {
                if (couponsRepo.countByCouponIdAndCustomerEmail(coupon.getId(), email) == 0) {
                    availableCoupons.add(getCoupons(coupon, cartTotal));
                }
            } else if (coupon.getCouponUsage().equals(CouponUsage.MULTIPLE)) {
                CouponUsed couponUsed = couponsUsedRepo.findByCustomerUserEmailAndCoupons_CouponCode(email, coupon.getCouponCode());
                if (couponUsed == null || couponUsed.getUsageCount() < coupon.getCouponUsageLimit()) {
                    availableCoupons.add(getCoupons(coupon, cartTotal));
                }
            }
        }

        return availableCoupons;
    }


    private CouponsResponse getCoupons(Coupons coupons, Double cartTotal) {
        double discountAvailableAt = coupons.getMinimumPurchaseAmount() - cartTotal;
        return CouponsResponse.builder()
                .couponCode(coupons.getCouponCode())
                .couponDescription(coupons.getCouponDescription())
                .discountAmount(coupons.getDiscountAmount())
                .discountPercentage(coupons.getDiscountPercentage())
                .maximumDiscountAmount(coupons.getMaximumDiscountAmount())
                .minimumPurchaseAmount(coupons.getMinimumPurchaseAmount())
                .couponExpiryDate(coupons.getCouponExpiryDate())
                .couponStartDate(coupons.getCouponStartDate())
                .name(coupons.getName())

                .message(coupons.getMinimumPurchaseAmount() > cartTotal ? "Add "+ discountAvailableAt +" Worth of Items More To Cart To Apply Coupon" : "Use Coupon Code  To Get " + coupons.getDiscountAmount() + " Off On Your Purchase")
                .build();

    }

    @Override
    public CouponsResponse getCoupon(String couponId) {
        return null;
    }

    @Override
    public String addCoupon(AddCouponsRequest addCouponsRequest) {
        Coupons coupons = Coupons.builder()
                .couponCategory(addCouponsRequest.getCouponCategory())
                .couponDescription(addCouponsRequest.getCouponDescription())
                .couponExpiryDate(addCouponsRequest.getCouponExpiryDate())
                .couponStartDate(addCouponsRequest.getCouponStartDate())
                .couponUsage(addCouponsRequest.getCouponUsage())
                .couponUsed(new ArrayList<>())
                .couponType(addCouponsRequest.getCouponType())
                .discountAmount(addCouponsRequest.getDiscountAmount())
                .discountPercentage(addCouponsRequest.getDiscountPercentage())
                .maximumDiscountAmount(addCouponsRequest.getMaximumDiscountAmount())
                .minimumPurchaseAmount(addCouponsRequest.getMinimumPurchaseAmount())
                .name(addCouponsRequest.getName())
                .couponActive(false)
                .build();

            if(addCouponsRequest.getCouponUsage().equals(CouponUsage.SINGLE)){
                coupons.setCouponUsageLimit(1);
            }
            else{
                coupons.setCouponUsageLimit(addCouponsRequest.getCouponUsageLimit());
            }

        couponsRepo.save(coupons);
        return "SUCCESS";
    }

    @Override
    @Transactional
    public String deleteCoupon(String couponId) {
        var coupon = couponsRepo.findByCouponCode(couponId);
        couponsUsedRepo.findByCoupons_CouponCode(couponId).forEach(couponsUsedRepo::delete);
        couponsRepo.delete(coupon);
         return "SUCCESS";
    }

    @Override
    public String updateCoupon(AddCouponsRequest addCouponsRequest, String couponId) {
        var coupon = couponsRepo.findByCouponCode(couponId);
        coupon.setCouponDescription(addCouponsRequest.getCouponDescription());
        coupon.setCouponExpiryDate(addCouponsRequest.getCouponExpiryDate());
        coupon.setCouponStartDate(addCouponsRequest.getCouponStartDate());
        coupon.setCouponUsage(addCouponsRequest.getCouponUsage());

        if(addCouponsRequest.getCouponUsage().equals(CouponUsage.SINGLE)){
            coupon.setCouponUsageLimit(1);
        }
        else{
            coupon.setCouponUsageLimit(addCouponsRequest.getCouponUsageLimit());
        }

        couponsRepo.save(coupon);
        return "SUCCESS";
    }

    @Override
    public String applyCoupon(String couponId, String email) {
        var coupon = couponsRepo.findByCouponCode(couponId);
        var customer = customerServices.getCustomer(email);

        // Handle the case where couponUsedRepo.findByCustomerUserEmailAndCoupons_CouponCode returns null
        var couponUsed = couponsUsedRepo.findByCustomerUserEmailAndCoupons_CouponCode(email, couponId);
        if (couponUsed == null) {
            couponUsed = CouponUsed.builder()
                    .coupons(coupon)
                    .customer(customer)
                    .usageCount(1)
                    .build();
        } else if (couponUsed.getUsageCount() <= coupon.getCouponUsageLimit()) {
            couponUsed.setUsageCount(couponUsed.getUsageCount() + 1);
        } else {
            return "COUPON_ALREADY_USED";
        }

        var cart = customer.getCart();
        var cartTotal = cart.getCartTotal();

        if (coupon == null || !coupon.isCouponValid()) {
            return "COUPON_NOT_VALID";
        }

        if (coupon.getCouponUsage().equals(CouponUsage.SINGLE)) {
            if (couponsRepo.countByCouponIdAndCustomerEmail(coupon.getId(), email) == 0) {
                if (coupon.isCouponValidForCart(cartTotal)) {
                    cart.setCartTotal(coupon.getDiscountAmount() > 0 ? cartTotal - coupon.getDiscountAmount() : cartTotal - (cartTotal * coupon.getDiscountPercentage() / 100));
                    cartRepo.save(cart);
                    couponsUsedRepo.save(couponUsed);
                    return "COUPON_APPLIED";
                }
                return "COUPON_NOT_VALID_FOR_CART";
            }
            return "COUPON_ALREADY_USED";
        } else {
            if (couponUsed.getUsageCount() < coupon.getCouponUsageLimit()) {
                if (coupon.isCouponValidForCart(cartTotal)) {
                    cart.setCartTotal(coupon.getDiscountAmount() > 0 ? cartTotal - coupon.getDiscountAmount() : cartTotal - (cartTotal * coupon.getDiscountPercentage() / 100));
                    cartRepo.save(cart);
                    couponsUsedRepo.save(couponUsed);
                    return "COUPON_APPLIED";
                }
                return "COUPON_NOT_VALID_FOR_CART";
            }
            return "COUPON_ALREADY_USED";
        }
    }

    @Override
    public HashMap<String, String> validateCoupon(String couponId, String email) {
        Coupons coupon = couponsRepo.findByCouponCode(couponId);
        var couponUsed = couponsUsedRepo.findByCustomerUserEmailAndCoupons_CouponCode(email, couponId);
        HashMap<String, String> response = new HashMap<>();

        if (coupon == null || !coupon.isCouponValid()) {
            response.put("message", "COUPON_NOT_VALID");
            return response;
        }



        if (!coupon.isCouponValidForCart(cart.getCartTotal(email))) {
            response.put("message", "COUPON_NOT_VALID_FOR_CART");
            return response;
        }
        if(couponUsed != null && couponUsed.getUsageCount() >= coupon.getCouponUsageLimit()){
            response.put("message", "COUPON_ALREADY_USED");
            return response;

        }
        if(coupon.getCouponExpiryDate().before(java.sql.Date.valueOf(java.time.LocalDate.now()))){
            response.put("message", "COUPON_EXPIRED");
            return response;
        }

        response.put("message", "COUPON_VALID");
        return response;
    }
}
