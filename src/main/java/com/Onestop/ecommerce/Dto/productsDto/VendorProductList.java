package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorProductList{  private String productId;
    private String name;
    private Map<String,String> imageURL;
    private Double regularPrice;
    private Double salePrice;
    private Integer stock;
    private Date innDate;
}
