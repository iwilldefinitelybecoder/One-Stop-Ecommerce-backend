package com.Onestop.ecommerce.Dto.CustomerDto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class WishListProductDetails {
    private String wishListId;
    private String productId;
    private String productName;
    private String productCategory;
    private Double regularPrice;
    private Double salePrice;
    private List<String> productImageURL;
    private Double productRating;
    private int productNumberOfRatings;
}
