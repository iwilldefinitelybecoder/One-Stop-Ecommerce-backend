package com.Onestop.ecommerce.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class PaymentKeys {

    @Value("${api.stripe.secret.key}")
    private String stripePrivateKey;
}
