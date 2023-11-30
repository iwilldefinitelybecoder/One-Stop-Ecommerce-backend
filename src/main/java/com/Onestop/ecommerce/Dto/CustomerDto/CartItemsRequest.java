package com.Onestop.ecommerce.Dto.CustomerDto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsRequest {
    private String productId;
    private Integer quantity;
    private String cartId;
    private String cartItemId;
}
