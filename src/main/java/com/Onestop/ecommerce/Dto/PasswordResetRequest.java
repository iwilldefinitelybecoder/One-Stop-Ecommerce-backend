package com.Onestop.ecommerce.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetRequest {
    private String email;
    private String password;
}
