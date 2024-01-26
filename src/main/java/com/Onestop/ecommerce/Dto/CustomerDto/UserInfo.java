package com.Onestop.ecommerce.Dto.CustomerDto;

import com.Onestop.ecommerce.Entity.Customer.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String firstName;
    private String lastName;
    private MembershipType membership;
    private String email;
    private Date dob;
    private double walletBalance;
    private Long phoneNumber;
}
