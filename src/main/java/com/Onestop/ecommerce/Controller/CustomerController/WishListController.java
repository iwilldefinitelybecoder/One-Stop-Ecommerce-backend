package com.Onestop.ecommerce.Controller.CustomerController;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Service.Customer.WishListServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer/wishlist")
@RequiredArgsConstructor
@Slf4j
public class WishListController {

    private final WishListServices wishListServices;

    @GetMapping("/getAll")
    public ResponseEntity<?> getWishList(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.getWishList(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }



    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteFromWishList(@RequestParam("productId") String productId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.deleteProductFromWishList(userName,productId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/add")
    public ResponseEntity<?> addProductToWishList(@RequestParam("productId") String productId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(userName);
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.addProductToWishList(userName,productId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/moveToCart")
    public ResponseEntity<?> moveAProductToWishList(@RequestParam("productId") String productId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.moveProductToCart(userName,productId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/moveAllToCart")
    public ResponseEntity<?> moveAllToCart(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.moveAllProductsToCart(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @GetMapping("/moveAllToWishList")
    public ResponseEntity<?> moveAllToWishList(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.moveAllProductsToWishList(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
    @GetMapping("/moveToWishList")
    public ResponseEntity<?> moveToWishList(@RequestParam("cartItemId") String cartItemId,@RequestParam("productId") String productId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.moveProductToWishList(userName,productId,cartItemId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/emptyWishList")
    public ResponseEntity<?> emptyWishList(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(wishListServices.emptyWishList(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
