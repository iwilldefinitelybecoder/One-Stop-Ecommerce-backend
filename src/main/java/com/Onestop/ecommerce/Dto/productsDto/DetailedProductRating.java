package com.Onestop.ecommerce.Dto.productsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DetailedProductRating {
    private Map<String,Integer> ratingData;
    private Integer totalRating;
    private Double averageRating;
    private String productId;
}
