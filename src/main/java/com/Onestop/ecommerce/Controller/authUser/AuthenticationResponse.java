package com.Onestop.ecommerce.Controller.authUser;

import com.Onestop.ecommerce.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private Long imageId;
    private Role role;


}
