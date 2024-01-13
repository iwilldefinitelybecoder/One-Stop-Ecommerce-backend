package com.Onestop.ecommerce.Dto.productsDto;

import com.Onestop.ecommerce.Entity.products.MetaAttribute;
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
public class ProductMajorDetails {
    private String productId;
    private String name;
    private List<String> imageURL;
    private Double regularPrice;
    private Double salePrice;
    private Double rating;
    private long numberOfRatings;
    private boolean isPublished;
    private Integer stock;
    private MetaAttribute metaAttribute;
    private double revenue;
    private int productSold;
    private Date innDate;

}
