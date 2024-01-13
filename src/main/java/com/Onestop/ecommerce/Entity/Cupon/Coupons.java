package com.Onestop.ecommerce.Entity.Cupon;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String couponCode;
    private String couponDescription;
    private Date couponExpiryDate;
    private Boolean couponActive;
    private Date couponStartDate;
    private String name;
    private double discountPercentage;
    private double discountAmount;
    private double minimumPurchaseAmount;
    private double maximumDiscountAmount;
    @Enumerated(EnumType.STRING)
    private CouponType couponType;
    private String couponCategory;
    private int couponUsageLimit;
    @Enumerated(EnumType.STRING)
    private CouponUsage couponUsage;

    @OneToMany(mappedBy = "coupons", cascade = CascadeType.ALL)
    private List<CouponUsed> couponUsed = new ArrayList<>();




    @PrePersist
    public void prePersist(){
        this.couponCode = UUID.randomUUID().toString();
    }

    public boolean isCouponValid(){
        Date currentDate = new Date();
        return this.couponActive && currentDate.after(this.couponStartDate) && currentDate.before(this.couponExpiryDate);
    }

    public boolean isCouponValidForCart(double cartTotal){
        return this.isCouponValid() && cartTotal >= this.minimumPurchaseAmount;
    }

    public double getDiscountAmount(double cartTotal){
        if(this.couponType.equals("PERCENTAGE")){
            return cartTotal * this.discountPercentage / 100;
        }
        return this.discountAmount;
    }

    public boolean isCouponValidForCustomer(String email){
        return this.couponUsage.equals("UNLIMITED") || this.couponUsage.equals("SINGLE") ;
    }

    public boolean isCouponValidForCategory(String category){
        return this.couponCategory.equals("ALL") || this.couponCategory.equals(category);
    }

    public boolean isCouponValidForProduct(String productId){
        return this.couponCategory.equals("ALL") || this.couponCategory.equals(productId);
    }

    public boolean isCouponValidForOrder(double orderTotal){
        return this.isCouponValid() && orderTotal >= this.minimumPurchaseAmount;
    }





}
