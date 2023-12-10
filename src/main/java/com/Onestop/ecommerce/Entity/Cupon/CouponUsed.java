package com.Onestop.ecommerce.Entity.Cupon;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class CouponUsed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Date usedDate;
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupons coupons;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
