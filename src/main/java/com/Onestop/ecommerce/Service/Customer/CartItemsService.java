package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;

public interface CartItemsService {
    String addProductToCart(String email, CartItemsRequest cartItemsRequest);
    String removeProductFromCart(String email,String cartItemId);
    String updateProductQuantity(String email,CartItemsRequest cartItemsRequest);
}
