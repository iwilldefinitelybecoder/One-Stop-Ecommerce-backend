package com.Onestop.ecommerce.Controller.PaymentController;

import com.Onestop.ecommerce.Dto.PaymentDto.StripeChargeCard;
import com.Onestop.ecommerce.Dto.PaymentDto.StripeToken;
import com.Onestop.ecommerce.Service.PaymentService.StripePaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment/stripe")
@RequiredArgsConstructor
public class StripeApiController {

    private final StripePaymentServices stripeServices;

    @PostMapping("/charge")
    public ResponseEntity<?> chargeCard(@RequestBody StripeChargeCard chargeRequest) {

        try {
            StripeChargeCard chargeCard = stripeServices.chargeCard(chargeRequest);
            if (chargeCard.isSuccess()) {
                return ResponseEntity.ok(chargeCard);
            } else {
                return ResponseEntity.badRequest().body(chargeCard);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error occurred while charging card");
        }
    }

    @PostMapping("/card/token")
    public ResponseEntity<?> createCardToken(@RequestBody StripeToken token) {
        try {
            StripeToken chargeCard = stripeServices.createCardToken(token);
            if (chargeCard.isSuccess()) {
                return ResponseEntity.ok(chargeCard);
            } else {
                return ResponseEntity.badRequest().body(chargeCard);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error occurred while creating card token");
        }
    }
}
