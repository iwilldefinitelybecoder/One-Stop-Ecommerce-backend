package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMinorDetails {
    private String productId;
    private String name;
    private String imageURL;
    private Double regularPrice;
    private Double salePrice;
    private Integer stock;
    private Date innDate;
}
