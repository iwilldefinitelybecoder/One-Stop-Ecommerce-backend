package com.Onestop.ecommerce.Controller.CustomerController;

import com.Onestop.ecommerce.Dto.CustomerDto.CardRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.Orders.OrderRequest;
import com.Onestop.ecommerce.Service.Customer.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderServices orderServices;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCartItems(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(orderServices.getAllOrders(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/cancelOrder")
    public ResponseEntity<?> updateItem(@RequestBody CardRequest request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return null;
//            return ResponseEntity.ok(cardServices.updateCard(request,userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> addItem(@RequestBody OrderRequest request) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {

            return ResponseEntity.ok(orderServices.createOrder(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }



    @GetMapping("/getCard")
    public ResponseEntity<?> getCard(@RequestParam String cardId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return null;
//            return ResponseEntity.ok(orderServices.getOrder());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
