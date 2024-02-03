package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemResponse;
import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsResponse;
import com.Onestop.ecommerce.Dto.CustomerDto.ProductInfo;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.products.resourceDetails;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    public ProductInfo addProductToCart(String email, CartItemsRequest cartItemsRequest) {
        var customer = getCartByCustomer(email);
        var product = productRepo.findByIdentifier(cartItemsRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        var cart = customer.getCart();
        var cartItems = cart.getItems();
        log.info("{}", cart.getItems().size());
        var cartItem = cartItemsRepo.findByCartIdAndProductIdentifier(cart.getId(),cartItemsRequest.getProductId()).orElse(null);

        if(cartItem == null){
            var cartItem1 = Items.builder()
                   .cart(cart)
                   .quantity(cartItemsRequest.getQuantity())
                    .totalPrice(cartItemsRequest.getQuantity() * product.getSalePrice() > 0 ? product.getSalePrice() : product.getRegularPrice())
                   .product(product)
                    .identifier(UUID.randomUUID().toString())
                   .build();

            cartItems.add(cartItem1);
            cart.setItems(cartItems);
            cartRepo.save(updateCartTotal(cart));
            cartItemsRepo.save(cartItem1);
            log.info(product.getIdentifier());
            return ProductInfo.builder()
                    .productName(product.getName())
                    .productImageURL(parseImageURL(product.getImages()))
                    .salePrice(product.getSalePrice())
                    .productQuantity(cartItem1.getQuantity())
                    .regularPrice(product.getRegularPrice())
                    .productTotal(cartItem1.getTotalPrice())
                    .productId(product.getIdentifier())
                    .cartItemsId(cartItem1.getIdentifier())
                    .build();


        }
      throw new RuntimeException("Product already in cart");
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
    public ProductInfo updateProductQuantity(String email, CartItemsRequest cartItemsRequest) {
        var cartItem = cartItemsRepo.findByIdentifier(cartItemsRequest.getCartItemId()).orElseThrow(() -> new RuntimeException("Cart item not found"));
        cartItem.setQuantity(cartItemsRequest.getQuantity());
        cartItem.setTotalPrice(cartItem.getProduct().getSalePrice() != 0 ? cartItem.getQuantity() * cartItem.getProduct().getSalePrice() : cartItem.getQuantity() * cartItem.getProduct().getRegularPrice());
        cartItemsRepo.save(cartItem);
        updateItemTotal(email);
        return ProductInfo.builder()
                .productName(cartItem.getProduct().getName())
                .productImageURL(parseImageURL(cartItem.getProduct().getImages()))
                .salePrice(cartItem.getProduct().getSalePrice())
                .productQuantity(cartItem.getQuantity())
                .regularPrice(cartItem.getProduct().getRegularPrice())
                .productTotal(cartItem.getTotalPrice())
                .productId(cartItem.getProduct().getIdentifier())
                .cartItemsId(cartItem.getIdentifier())
                .build();
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
    private List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }
}

