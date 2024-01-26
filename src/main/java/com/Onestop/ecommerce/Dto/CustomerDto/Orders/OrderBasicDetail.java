package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderBasicDetail {
    private String orderId;
    private String orderDate;
    private OrderStatus orderStatus;
    private Double grandTotal;
    private String generatedOrderId;
}
