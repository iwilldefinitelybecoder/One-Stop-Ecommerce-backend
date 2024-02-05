package com.Onestop.ecommerce.Controller.vendor;

import com.Onestop.ecommerce.Dto.CustomerDto.AddressRequest;
import com.Onestop.ecommerce.Service.VendorServices.VendorServices;
import com.Onestop.ecommerce.Service.products.ProductsServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor")
@Slf4j
@RequiredArgsConstructor
public class VendorController {

    private final VendorServices services;

    private final ProductsServices productsServices;

     @PostMapping("/register")
    public ResponseEntity<?> authenticate(@ModelAttribute VendorRequest request) {
        return ResponseEntity.status(200).body(services.register(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
    String userName =  SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(200).body(services.authenticate(userName));
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllAddress(){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {

            return ResponseEntity.status(HttpStatus.OK).body(services.getAllProducts(userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }



    }

    @GetMapping("/getVendorProducts")
    public ResponseEntity<?> getProductMajorDetails() {
        var userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return ResponseEntity.status(200).body(productsServices.getVendorProducts(userName));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAddress(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(services.deleteProduct(identifier,userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }



    @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestParam("identifier") String identifier,@RequestBody AddressRequest address){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(services.updateProduct(address,identifier));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/get")
    public ResponseEntity<?> getAddress(@RequestParam("identifier") String identifier){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(services.getProductDetails(identifier));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/getDashboardData")
    public ResponseEntity<?> getDashBoardData(@RequestParam(value = "productId",required = false) String productId){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try
        {
            return ResponseEntity.status(HttpStatus.OK).body(services.getDashboardData(productId,userName));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


}
