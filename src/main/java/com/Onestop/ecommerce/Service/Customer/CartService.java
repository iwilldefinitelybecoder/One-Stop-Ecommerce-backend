package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsResponse;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.cart.Cart;

public interface CartService {
    Cart getCartByUserEmail(String email);
    String emptyCart(String email);
    CartItemsResponse getAllCartItems(String email);
    Double getCartTotal(String email);
    Integer getCartItemsCount(String email);

}
