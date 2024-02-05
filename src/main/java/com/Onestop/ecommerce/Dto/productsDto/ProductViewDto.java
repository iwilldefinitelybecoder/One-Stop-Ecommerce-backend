package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductViewDto {
    private double rating;
    private String name;
    private boolean published;
    private String productId;
    private Integer stock;
    private Double salePrice;
    private Double regularPrice;
    private Map<String,String> thumbnail;
    private String category;
    private String imagePreview;
    private List<Map<String,String>> imageURL;
}
