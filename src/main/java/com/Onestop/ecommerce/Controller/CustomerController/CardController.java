package com.Onestop.ecommerce.Controller.CustomerController;


import com.Onestop.ecommerce.Dto.CustomerDto.CardRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.CartItemsRequest;
import com.Onestop.ecommerce.Service.Customer.CardServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardServices cardServices;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCartItems(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cardServices.getCards(userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/updateItem")
    public ResponseEntity<?> updateItem(@RequestBody CardRequest request){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cardServices.updateCard(request,userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @DeleteMapping("/deleteItem")
    public ResponseEntity<?> deleteItem(@RequestParam String cardId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cardServices.deleteCard(cardId,userName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody CardRequest request) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {

            return ResponseEntity.ok(cardServices.addCard(request, userName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PatchMapping("/setDefault")
    public ResponseEntity<?> setDefault(@RequestParam String cardId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cardServices.SetDefaultCard(cardId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/getCard")
    public ResponseEntity<?> getCard(@RequestParam String cardId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.ok(cardServices.getCard(cardId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
