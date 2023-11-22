package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemsServices implements CartItemsService{

    private final CartServices cartServices;
    private final CustomerServices customerServices;
    private final CartItemsRepo cartItemsRepo;
    private final CartRepo cartRepo;
    private final productsRepo productRepo;


    private Customer getCartByCustomer(String email){
        return customerServices.getCustomer(email);
    }
    @Override
    @Transactional
    public String addProductToCart(String email, CartItemsRequest cartItemsRequest) {
        var customer = getCartByCustomer(email);
        var product = productRepo.findByIdentifier(cartItemsRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        var cart = customer.getCart();
        var cartItems = cart.getItems();
        var cartItem = cartItemsRepo.findByProductIdentifier(cartItemsRequest.getProductId()).orElse(null);
        log.info("cartItem: {}", cartItemsRequest.getProductId());
        if(cartItem == null){
            var cartItem1 = Items.builder()
                   .cart(cart)
                   .quantity(cartItemsRequest.getQuantity())
                   .product(product)
                    .identifier(UUID.randomUUID().toString())
                   .build();
            cartItems.add(cartItem1);
            cart.setItems(cartItems);
            cartRepo.save(updateCartTotal(cart));
            cartItemsRepo.save(cartItem1);
            return "Product added to cart successfully";
        }
      return "PRODUCT_EXISTS";
    }

    private Cart updateCartTotal(Cart cart){
        var cartItems = cart.getItems();
        var cartTotal = 0.0;
        for (Items cartItem : cartItems) {
            cartTotal += cartItem.getProduct().getSalePrice() * cartItem.getQuantity();
        }
        cart.setCartTotal(cartTotal);
        return cart;
    }

    @Override
    @Transactional
    public String removeProductFromCart(String email, String cartItemIdentifier) {
        var cartItem = cartItemsRepo.findByIdentifier(cartItemIdentifier).orElseThrow(() -> new RuntimeException("Cart item not found"));
        var Customer = getCartByCustomer(email);
        var cart = Customer.getCart();
        var cartItems = cart.getItems();
        cartItems.remove(cartItem);
        cart.setItems(cartItems);
        cartRepo.save(updateCartTotal(cart));
        cartItemsRepo.delete(cartItem);
        return "Product removed from cart successfully";
    }

    @Override
    @Transactional
    public String updateProductQuantity(String email, CartItemsRequest cartItemsRequest) {
        var cartItem = cartItemsRepo.findByIdentifier(cartItemsRequest.getCartItemId()).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(cartItemsRequest.getQuantity());
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getProduct().getSalePrice());
        cartItemsRepo.save(cartItem);
        updateItemTotal(email);
        return "Product quantity updated successfully";
    }

    private void updateItemTotal(String email){
        var customer = getCartByCustomer(email);
        var cart = customer.getCart();
        var cartItems = cart.getItems();
        Double cartTotal = 0.0;
        for (Items cartItem : cartItems) {
            cartTotal += cartItem.getTotalPrice();
        }
        cart.setCartTotal(cartTotal);
        cartRepo.save(cart);
    }
}
