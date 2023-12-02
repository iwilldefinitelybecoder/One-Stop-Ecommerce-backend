package com.Onestop.ecommerce.Dto.CustomerDto;

import com.Onestop.ecommerce.Entity.Customer.card.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expireDate;
    private CardType cardType;
    private String cvc;
    private String cardId;
}
