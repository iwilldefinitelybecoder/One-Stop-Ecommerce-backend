package com.Onestop.ecommerce.Dto.CustomerDto.Orders;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecureCardResponse {
    private String cardNumber;
 private LocalDate expireDate;
 private String cvc;
}
