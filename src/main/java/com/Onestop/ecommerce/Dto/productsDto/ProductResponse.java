package com.Onestop.ecommerce.Dto.productsDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ProductResponse {
    private Double regularPrice;
    private String name;
    private Double salePrice;
    private List<String> imageURL;
    private String productId;
    private String description;
    private String category;
    private Double rating;
    private Map<String,?> extraAttributes;
    private long numberOfRatings;
}
