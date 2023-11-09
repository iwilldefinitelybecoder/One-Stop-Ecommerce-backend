package com.Onestop.ecommerce.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
}
