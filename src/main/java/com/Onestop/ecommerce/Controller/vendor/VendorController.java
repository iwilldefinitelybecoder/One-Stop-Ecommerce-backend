package com.Onestop.ecommerce.Controller.vendor;

import com.Onestop.ecommerce.Service.VendorServices.VendorServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vendor")
@Slf4j
@RequiredArgsConstructor
public class VendorController {

    private final VendorServices services;

     @PostMapping("/register")
    public ResponseEntity<?> authenticate(@RequestBody VendorRequest request) {
        return ResponseEntity.status(200).body(services.register(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
    String userName =  SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(200).body(services.authenticate(userName));
    }
}
