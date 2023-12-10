package com.Onestop.ecommerce.Dto.CustomerDto;

import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class  WishListResponse {
    private String wishListId;
    private List<ProductResponse> products;
    private Integer totalItems;
}
