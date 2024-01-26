package com.Onestop.ecommerce.Controller.CustomerController;

import com.Onestop.ecommerce.Dto.CustomerDto.UserInfo;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import com.Onestop.ecommerce.Service.Customer.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customer/info")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final OrdersService ordersService;
    private final CustomerServices customerServices;

    @GetMapping("/getOrderInfo")
    public ResponseEntity<?> getCard(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(ordersService.getCustomerOrdersInfo(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserInfo request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(customerServices.updateCustomerInfo(request,userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getUserInfo")
    public ResponseEntity<?> getUserInfo(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(customerServices.getUserInfo(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
