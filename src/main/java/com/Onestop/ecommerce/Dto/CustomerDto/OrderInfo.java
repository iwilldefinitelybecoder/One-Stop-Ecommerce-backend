package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {

    private Long allOrders;
    private Long awaitingPayment;
    private Long awaitingShipment;
    private Long awaitingDelivery;
}
