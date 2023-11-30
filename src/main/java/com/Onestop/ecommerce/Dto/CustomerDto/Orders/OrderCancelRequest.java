package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCancelRequest {
    String orderId;
    String customerId;
    String reason;
    String comment;
    String refundType;
    String refundAmount;
    String orderItemId;
}
