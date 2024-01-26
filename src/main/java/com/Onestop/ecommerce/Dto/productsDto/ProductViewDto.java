package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductViewDto {
    private List<String> imageURL;
    private double rating;
    private String name;
    private boolean published;
    private String productId;
    private Integer stock;
    private Double salePrice;
    private Double regularPrice;
    private String thumbnail;
    private String category;
}
