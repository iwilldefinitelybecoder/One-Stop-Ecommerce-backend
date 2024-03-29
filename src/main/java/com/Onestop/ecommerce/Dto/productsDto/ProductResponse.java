package com.Onestop.ecommerce.Dto.productsDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ProductResponse {
    private Double regularPrice;
    private String name;
    private Double salePrice;
    private List<Map<String,String>> imageURL;
    private String productId;
    private String description;
    private String category;
    private Double rating;
    private Date innDate;
    private Map<String,?> extraAttributes;
    private long numberOfRatings;
    private Map<String,String> thumbnail;
    private boolean isPublished;
    private String brand;
    private String vendorName;
    private String imagePreview;
    private Integer stock;

}
