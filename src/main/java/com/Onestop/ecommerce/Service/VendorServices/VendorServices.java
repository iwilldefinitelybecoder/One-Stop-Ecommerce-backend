package com.Onestop.ecommerce.Service.VendorServices;

import com.Onestop.ecommerce.Controller.vendor.VendorRequest;
import com.Onestop.ecommerce.Entity.Role;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import com.Onestop.ecommerce.Repository.VendorRepo.VendorRepository;
import com.Onestop.ecommerce.Repository.userRepo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VendorServices {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    public String register(VendorRequest request) {

        var user = userRepository.findByEmail(request.getUserEmail()).orElse(null);
        if(user == null){
            return "NULL";
        }
        var vendor  = Vendor.builder()
                .user(user)
                .email(request.getVendorEmail())
                .vendorCompanyName(request.getVendorCompanyName())
                .build();
        vendorRepository.save(vendor);
        user.setRole(Role.VENDOR);
        userRepository.save(user);

        return "SUCCESS";


    }
}
