package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import com.Onestop.ecommerce.Entity.Payments.PaymentMethods;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    private ProductOrderDetails products;
    private String paymentId;
    private String couponId;
    private String orderId;
    private String shippingAddressId;
    private String billingAddressId;
    private String customerId;
    private String cardId;
    private Double ordertotal;
    private String couponCode;
    private String paymentProcessId;
    @Enumerated(EnumType.STRING)
    private PaymentMethods paymentMethod;
    private boolean useWallet;
    private boolean buyNow;
}
