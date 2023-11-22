package com.Onestop.ecommerce.Service.PaymentService;

import com.Onestop.ecommerce.Dto.PaymentDto.StripeChargeCard;
import com.Onestop.ecommerce.Dto.PaymentDto.StripeToken;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripePaymentServices {

    @Value("${api.stripe.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public StripeToken createCardToken(StripeToken chargeRequest) {
        try{
            Map<String, Object> card = new HashMap<>();
            card.put("number", chargeRequest.getCardNumber());
            card.put("exp_month", chargeRequest.getCardExpMonth());
            card.put("exp_year", chargeRequest.getCardExpYear());
            card.put("cvc", chargeRequest.getCardCVC());
            Map<String, Object> params = new HashMap<>();
            params.put("card", card);
            Token token = Token.create(params);
            if(token != null && token.getId() != null){
                chargeRequest.setToken(token.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error occurred while charging card. {}", e.getMessage());
            throw new RuntimeException(e);

        }
    }
    public StripeChargeCard chargeCard(StripeChargeCard chargeRequest){
        try{
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", chargeRequest.getAmount());
            chargeParams.put("currency", chargeRequest.getCurrency());
            chargeParams.put("description", "Charge for " + chargeRequest.getEmail());
            chargeParams.put("source", chargeRequest.getToken());
            Map<String, Object> initialMetadata = new HashMap<>();
            initialMetadata.put("id",chargeRequest.getChargeId());
            chargeParams.putAll(chargeRequest.getAdditionalInfo());
            var charge = Charge.create(chargeParams);
            if(charge.getPaid()){
                chargeRequest.setChargeId(charge.getId());
                chargeRequest.setSuccess(true);
            }
            return chargeRequest;
        }catch (Exception e){
            e.printStackTrace();
            log.error("Error occurred while charging card. {}", e.getMessage());
            throw new RuntimeException(e);

        }
    }
}
