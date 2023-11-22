package com.Onestop.ecommerce.Dto.PaymentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeChargeCard {
    private String token;
    private String email;
    private String amount;
    private String currency;
    private boolean success;
    private String chargeId;
    private Map<String,Object> additionalInfo = new HashMap<>();
}
