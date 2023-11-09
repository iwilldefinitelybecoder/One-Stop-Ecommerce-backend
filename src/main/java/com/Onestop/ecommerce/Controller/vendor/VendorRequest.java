package com.Onestop.ecommerce.Controller.vendor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VendorRequest {
    private String userEmail;
    private String vendorEmail;
    private String VendorCompanyName;
}
