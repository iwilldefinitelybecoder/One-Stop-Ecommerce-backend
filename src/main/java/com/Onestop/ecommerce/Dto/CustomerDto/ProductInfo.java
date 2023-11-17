package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductInfo {
    private String cartItemsId;
    private Double regularPrice;
    private String productName;
    private Double salePrice;
    private int productQuantity;
    private Double productTotal;
    private List<String> productImageURL;
}
