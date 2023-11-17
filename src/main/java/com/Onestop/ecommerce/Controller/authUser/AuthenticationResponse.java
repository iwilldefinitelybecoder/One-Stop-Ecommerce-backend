package com.Onestop.ecommerce.Controller.authUser;

import com.Onestop.ecommerce.Entity.Role;
import com.Onestop.ecommerce.Entity.user.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collection;

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
    private Collection<String> roles=new ArrayList<>();
    private boolean isEnabled;



}
