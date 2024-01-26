package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemsResponse {

    private String cartId;
    private List<ProductInfo> productInfo;
    private Double cartTotal;
    private Integer totalItems;
    private Double discount;
    private Double shippingCharges;
    private Double tax;
    private Double wallet;
    private Double grandTotal;
   private String subTotal;

}

