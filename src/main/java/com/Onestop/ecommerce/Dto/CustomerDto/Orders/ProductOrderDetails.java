package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductOrderDetails {
    private String productId;
    private Integer quantity;

}
