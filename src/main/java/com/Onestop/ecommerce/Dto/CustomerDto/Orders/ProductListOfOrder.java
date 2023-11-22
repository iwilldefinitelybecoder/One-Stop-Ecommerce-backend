package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

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
    private Long quantity;
    private Double price;
    private Double totalPrice;
}
