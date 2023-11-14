package com.Onestop.ecommerce.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePasswordRequest {
    private String email;
    private String password;
    private String oldPassword;
}
