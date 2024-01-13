package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsResponse;
import com.Onestop.ecommerce.Dto.CustomerDto.ProductInfo;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.products.resourceDetails;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
@Slf4j
public class CartServices implements CartService {

    private final CartRepo cartRepo;

    private final CartItemsRepo cartItemsRepo;

    private final CustomerRepo customerRepo;

    @Override
    public Cart getCartByUserEmail(String email) {
          return cartRepo.findCartByUserEmail(email);

    }


    @Override
    @Transactional
    public String emptyCart(String email) {
        var customer = customerRepo.findByUserEmail(email);
        var cart = customer.getCart();
        cart.setCartTotal(0.0);
        cart.setItems(new ArrayList<>());
        cart.setCartTotal(0);
        cartRepo.save(cart);
        cartItemsRepo.deleteByCartId(cart.getId());
        return "Cart emptied successfully";
    }

    @Override
    public CartItemsResponse getAllCartItems(String email) {
        var customer =customerRepo.findByUserEmail(email);
        var cart = customer.getCart();
        var cartItems = cart.getItems();
        List<ProductInfo> info = new ArrayList<>();
        cartItems.forEach(cartItem -> {
                    var product = cartItem.getProduct();
                    
                    var productInfo = ProductInfo.builder()
                            .cartItemsId(cartItem.getIdentifier())
                            .productName(product.getName())
                            .regularPrice(product.getRegularPrice())
                            .salePrice(product.getSalePrice())
                            .productQuantity(cartItem.getQuantity())
                            .productId(product.getIdentifier())
                            .productImageURL(parseImageURL(product.getImages()))
                            .productTotal(cartItem.getTotalPrice())
                            .build();
                    info.add(productInfo);
                });
        return CartItemsResponse.builder()

                .totalItems(cartItems.size())
                .productInfo(info)
                .tax(info.size() * 0.18)
                .shippingCharges(info.size() * 0.05)
                .discount(0.0)
                .cartTotal(cart.getCartTotal())
                .grandTotal(cart.getCartTotal() + (info.size() * 0.18) + (info.size() * 0.05))

                .cartId(cart.getIdentifier())
                .build();
        };



    private List<String> parseImageURL(List<resourceDetails> images){
        List<String> imageURL = new ArrayList<>();
        images.forEach(image -> {
            var url = "http://localhost:8000/image-resources/product-Images/" + image.getUrl();
            imageURL.add(url);
        });
        return imageURL;
    }

    @Override
    public Double getCartTotal(String email) {
        var customer = customerRepo.findByUserEmail(email);
        if (customer.getCart() == null) {
            return 0.0;
        }
        return customer.getCart().getCartTotal();

    }

    @Override
    public Integer getCartItemsCount(String email) {
    var customer =customerRepo.findByUserEmail(email);
        return customer.getCart().getItems().size();
    }
}
