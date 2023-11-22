package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import com.Onestop.ecommerce.Entity.Payments.PaymentMethods;
import com.Onestop.ecommerce.Entity.orders.OrderItems;
import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private String orderId;
    private List<ProductListOfOrder>  productId;
    private Long quantity;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String paymentId;
    private String ShippingAddressId;
    private String BillingAddressId;
    private String couponId;
    private String TrackingId;
    private Date ExpectedDeliveryDate;
    private Date deliveryDate;
    private PaymentMethods paymentMethod;
    private boolean paymentStatus;
    private OrderSummary orderSummary;

}

