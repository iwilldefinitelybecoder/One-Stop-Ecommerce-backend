package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderSummary{
    private double itemsTotal;
    private double shippingTotal;
    private double taxTotal;
    private Long orderTotal;
    private String couponId;
    private Double discount;
    private double grandTotal;
}
