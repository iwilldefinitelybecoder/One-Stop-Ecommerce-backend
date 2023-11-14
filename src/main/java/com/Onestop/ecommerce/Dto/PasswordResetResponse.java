package com.Onestop.ecommerce.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetResponse {
    private String email;
    private Long ImageId;
    private String firstName;
    private String lastName;
}
