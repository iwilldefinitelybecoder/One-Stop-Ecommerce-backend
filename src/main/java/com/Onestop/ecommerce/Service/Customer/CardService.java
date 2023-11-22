package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CardRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.CardResponse;

import java.util.List;

public interface CardService {
    List<CardResponse> getCards(String email);
    String addCard(CardRequest cardRequest, String email);
    String deleteCard(String cardId, String email);
    String updateCard(CardRequest cardRequest, String cardId);
    String SetDefaultCard(String cardId);
    CardResponse getCard(String cardId);
}
