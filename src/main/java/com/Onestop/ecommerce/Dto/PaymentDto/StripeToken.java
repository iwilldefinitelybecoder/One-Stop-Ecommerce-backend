package com.Onestop.ecommerce.Dto.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeToken{
    private String cardNumber;
    private String cardExpMonth;
    private String cardExpYear;
    private String cardCVC;
    private String token;
    private String email;
    private boolean success;

}
