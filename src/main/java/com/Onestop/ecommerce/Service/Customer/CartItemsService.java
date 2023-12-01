package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.ProductInfo;

public interface CartItemsService {
    ProductInfo addProductToCart(String email, CartItemsRequest cartItemsRequest);
    String removeProductFromCart(String email,String cartItemId);
    ProductInfo updateProductQuantity(String email,CartItemsRequest cartItemsRequest);
}
