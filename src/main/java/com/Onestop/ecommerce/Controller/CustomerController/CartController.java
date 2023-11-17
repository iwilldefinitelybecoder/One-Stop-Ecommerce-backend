package com.Onestop.ecommerce.Controller.CustomerController;

import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;
import com.Onestop.ecommerce.Service.Customer.CartItemsServices;
import com.Onestop.ecommerce.Service.Customer.CartServices;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CustomerServices customerServices;
    private final CartServices cartServices;
    private final CartItemsServices cartItemsServices;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCartItems(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartServices.getAllCartItems(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/updateItem")
    public ResponseEntity<?> updateItem(@RequestBody CartItemsRequest cartItemsRequest){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartItemsServices.updateProductQuantity(userName,cartItemsRequest));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestParam String cartItemId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartItemsServices.removeProductFromCart(userName,cartItemId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody CartItemsRequest cartItemsRequest){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartItemsServices.addProductToCart(userName,cartItemsRequest));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/emptyCart")
    public ResponseEntity<?> emptyCart(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartServices.emptyCart(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getTotal")
    public ResponseEntity<?> getCartTotal(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cartServices.getCartTotal(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



}
