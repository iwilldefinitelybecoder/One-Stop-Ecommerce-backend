package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.WishListResponse;

import java.util.List;

public interface WishListService {
    WishListResponse getWishList(String customerId);
    String addProductToWishList(String customerId, String productId);
    String moveAllProductsToCart(String customerId);
    String moveAllProductsToWishList(String customerId);
    String moveProductToWishList(String customerId, String productId, String cartItemId);
    String moveProductToCart(String customerId, String productId);
    String deleteProductFromWishList(String customerId, String productId);

    String emptyWishList(String customerId);

}
