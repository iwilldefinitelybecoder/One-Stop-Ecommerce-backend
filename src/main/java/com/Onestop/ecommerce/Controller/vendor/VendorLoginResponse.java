package com.Onestop.ecommerce.Controller.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendorLoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Long imageId;
    private String role;
    private Long vendorId;
}
