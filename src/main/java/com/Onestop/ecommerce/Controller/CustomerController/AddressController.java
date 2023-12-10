package com.Onestop.ecommerce.Controller.CustomerController;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Service.Customer.AddressServices;
import com.Onestop.ecommerce.Service.Customer.CustomerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1/customer/address")
@RequiredArgsConstructor
public class AddressController {

    private final CustomerServices customerServices;
    private final AddressServices addressServices;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAddress(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.status(HttpStatus.OK).body(customerServices.getCustomerAddress(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }



    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAddress(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(addressServices.deleteAddress(identifier,userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody AddressRequest address){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(userName);
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(addressServices.saveAddress(address,userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestParam("identifier") String identifier,@RequestBody AddressRequest address){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(addressServices.updateAddress(address,identifier));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/get")
    public ResponseEntity<?> getAddress(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(addressServices.getAddress(identifier));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
