package com.Onestop.ecommerce.Controller.vendor;

import com.Onestop.ecommerce.Entity.user.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

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
    private Collection<RoleEntity> roles;
    private Long vendorId;
}
