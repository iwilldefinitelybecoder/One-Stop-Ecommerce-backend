package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductListOfOrder {
    private String productId;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
    private String purchaseId;
    private String orderItemId;
    private String trackingId;
    private String name;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
