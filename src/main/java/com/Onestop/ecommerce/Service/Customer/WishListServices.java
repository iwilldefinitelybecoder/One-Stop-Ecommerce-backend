package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.WishListProductDetails;
import com.Onestop.ecommerce.Dto.CustomerDto.WishListResponse;
import com.Onestop.ecommerce.Dto.productsDto.ProductResponse;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.Items;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartItemsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CartRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.WishListRepo;
import com.Onestop.ecommerce.Repository.products.productsRepo;
import com.Onestop.ecommerce.utils.ImplFunction;
import com.stripe.service.CustomerService;
import com.stripe.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class WishListServices implements WishListService {

    private final CustomerServices customerService;
    private final productsRepo ProductsRepo;
    private final WishListRepo wishListRepo;
    private final productsRepo productsRepo;

    private final CustomerRepo customerRepo;
    private final CartRepo cartRepo;
    private final CartItemsRepo cartItemsRepo;



    @Override
    public WishListResponse getWishList(String email) {
        Customer customer = customerService.getCustomer(email);
        WishList wishList = customer.getWishList();
        List<ProductResponse> products = new ArrayList<>();
        wishList.getProduct().forEach(product -> {
            var response = ProductResponse.builder()
                    .name(product.getName())
                    .description(product.getDescription())
                    .category(product.getCategory())
                    .regularPrice(product.getRegularPrice())
                    .imageURL(ImplFunction.parseImageURL(product.getImages()))
                    .productId(product.getIdentifier())
                    .isPublished(product.isEnabled())
                    .numberOfRatings(product.getReviews().size())
                    .rating(product.getAverageRating())
                    .build();
            if(product.getSalePrice() != 0){
                response.setSalePrice(product.getSalePrice());
            }
            products.add(response);
        });
        return WishListResponse.builder()
                .wishListId(wishList.getIdentifier())
                .products(products)
                .totalItems(wishList.getProduct().size())
                .build();
    }

    @Override
    @Transactional
    public String addProductToWishList(String email, String productId) {
        Customer customer = customerService.getCustomer(email);
        WishList wishList = customer.getWishList();
        var product = productsRepo.findByIdentifier(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        wishList.getProduct().add(product);
        customer.getWishList().setProduct(wishList.getProduct());

        wishListRepo.save(wishList);
        customerRepo.save(customer);
        return "Product added to wish list";
    }

    @Override
    @Transactional
    public String deleteProductFromWishList(String customerId, String productId) {
        Customer customer = customerService.getCustomer(customerId);
        WishList wishList = customer.getWishList();
        var product = productsRepo.findByIdentifier(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        wishList.getProduct().remove(product);
        customer.getWishList().setProduct(wishList.getProduct());
        wishListRepo.save(wishList);
        customerRepo.save(customer);
        return "Product deleted from wish list";
    }

    @Override
    @Transactional
    public String moveProductToWishList(String customerId, String productId,String CartItemId) {
        Customer customer = customerService.getCustomer(customerId);
        Items items = cartItemsRepo.findByIdentifier(CartItemId).orElseThrow(() -> new RuntimeException("Cart item not found"));
        var product = productsRepo.findByIdentifier(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Cart cart = cartRepo.findCartByUserEmail(customer.getUser().getEmail());

        Cart customerCart = customer.getCart();
        customerCart.getItems().remove(items);
        cartItemsRepo.delete(items);
        cart.getItems().remove(items);
        cartRepo.save(cart);

        WishList wishList = customer.getWishList();
        wishList.getProduct().add(items.getProduct());
        List<WishList> wishList1 = wishListRepo.findByCustomer(customer);
        wishList1.add(wishList);
        customer.getWishList().setProduct(wishList.getProduct());

        wishListRepo.save(wishList);
        customerRepo.save(customer);
        return "Product moved to wish list";
    }

    @Override
    public String emptyWishList(String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        WishList wishList = customer.getWishList();
        wishList.getProduct().removeAll(wishList.getProduct());
        wishListRepo.save(wishList);
        return "Wish list emptied";
    }

    @Override
    @Transactional
    public String moveAllProductsToCart(String customerId) {
        //fetch all necessary data from Db
        Customer customer = customerService.getCustomer(customerId);
        Cart cart = cartRepo.findCartByUserEmail(customer.getUser().getEmail());
        WishList wishList = wishListRepo.findByIdentifier(customer.getWishList().getIdentifier());
        Collection<Product> products = wishList.getProduct();
        List<Items> items = new ArrayList<>();

        products.forEach(product -> {
            if(!cart.getItems().contains(product) &&product.isEnabled()) {
                Items item = Items.builder()
                        .product(product)
                        .quantity(1)
                        .cart(cart)
                        .build();
                items.add(item);
                cartItemsRepo.save(item);
            }else {

            }
        });

        cart.getItems().addAll(items);
        cartRepo.save(cart);
        wishList.getProduct().removeAll(products);
        wishListRepo.save(wishList);
        return "All products moved to cart";

    }

    @Override
    public String moveAllProductsToWishList(String customerId) {
        Customer customer = customerService.getCustomer(customerId);
        Cart cart  = customer.getCart();
        WishList wishList = customer.getWishList();
        List<Product> products = new ArrayList<>();
        cart.getItems().forEach(items -> {
            if(!wishList.getProduct().contains(items.getProduct())){
                products.add(items.getProduct());
            }
            cartItemsRepo.delete(items);
        });

        wishList.getProduct().addAll(products);
        wishListRepo.save(wishList);
        cart.getItems().removeAll(cart.getItems());
        cartRepo.save(cart);
        return "All products moved to wish list";
    }

    @Override
    @Transactional
    public String moveProductToCart(String customerId, String productId) {
        Customer customer = customerService.getCustomer(customerId);
        WishList wishList = customer.getWishList();
        var product = productsRepo.findByIdentifier(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        var itemss = customer.getCart().getItems();
        if(itemss.contains(product)){
            return "Product already in cart";
        };

        if(product.isEnabled()) {
            Cart cart = cartRepo.findCartByUserEmail(customer.getUser().getEmail());
            Items items = Items.builder()
                    .product(product)
                    .quantity(1)
                    .cart(cart)
                    .totalPrice(product.getSalePrice() > 0 ? product.getSalePrice() : product.getRegularPrice())
                    .build();
            cart.getItems().add(items);
            cartItemsRepo.save(items);
            cartRepo.save(cart);
            wishList.getProduct().remove(product);
            wishListRepo.save(wishList);
        }else{
            throw new RuntimeException("Product is disabled");
        }
        return "Product moved to cart";
    }
}
