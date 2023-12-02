package com.Onestop.ecommerce.Service.Customer;

import com.Onestop.ecommerce.Dto.CustomerDto.CardRequest;
import com.Onestop.ecommerce.Dto.CustomerDto.CardResponse;
import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Repository.CustomerRepo.CardsRepo;
import com.Onestop.ecommerce.Repository.CustomerRepo.CustomerRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardServices implements CardService {
    private final CustomerRepo customerRepo;
    private final CardsRepo cardsRepo;

    private Customer getCustomer(String email) {
        return customerRepo.findByUserEmail(email);
    }
    @Override
    public List<CardResponse> getCards(String email) {
        Customer customer = getCustomer(email);
        log.info(email);
        var cards = customer.getPaymentCards();
        List<CardResponse> customerCards= new ArrayList<>();
        cards.forEach(card -> {
            var response = CardResponse.builder()
                    .cardId(card.getIdentifier())
                    .cardNumber( ("**** **** **** " + card.getNumber().toString().substring(12)))
                    .cardHolderName(card.getName())
                    .cardType(card.getType())
                    .expireDate(formatToDateString((card.getExpireDate())))
                    .defaultCard(card.getIsDefault())
                    .build();
            customerCards.add(response);

        });
        return customerCards;

    }

    @Override
    public String addCard(CardRequest cardRequest, String email) {
        Customer customer = getCustomer(email);
        var cards = customer.getPaymentCards();
        var card = cardsRepo.findByIdentifier(cardRequest.getCardId()).orElse(null);
        if(cards.contains(card)){
            return "Card already exists";
        }
        Long cardNumber = Long.parseLong(cardRequest.getCardNumber().replaceAll("\\s+",""));
        var newCard = Cards.builder()
                .number(CheckIfCardExists(cardNumber))
                .name(cardRequest.getCardHolderName())
                .expireDate(formatToLocalDate(cardRequest.getExpireDate()))
                .cvc(cardRequest.getCvc())
                .type(cardRequest.getCardType())
                .customer(customer)
                .isDefault(false)
                .build();
        cardsRepo.save(newCard);
        cards.add(newCard);
        customer.setPaymentCards(cards);
        customerRepo.save(customer);
        return "Card added successfully";
    }

    private LocalDate formatToLocalDate(String date){
        String year = date.substring(3,5);
        String month = date.substring(0,2);
        int parsedYear = Integer.parseInt("20" + year);
        int parsedMonth = Integer.parseInt(month);
        int parsedDay = 1;
        return LocalDate.of(parsedYear, parsedMonth, parsedDay);
    }

    private Long CheckIfCardExists(Long cardNumber){
        if(cardsRepo.findByNumber(cardNumber).isPresent()){
            throw new RuntimeException("Card already exists");
        }
        return cardNumber;
    }


    @Override
    @Transactional
    public String deleteCard(String cardId, String email) {
        Customer customer = getCustomer(email);
        var cards = customer.getPaymentCards();
        var card = cardsRepo.findByIdentifier(cardId).orElse(null);
        if(!cards.contains(card)){
            return "Card does not exist";
        }
        cards.remove(card);
        customer.setPaymentCards(cards);
        customerRepo.save(customer);
        cardsRepo.delete(card);
        return "Card deleted successfully";
    }

    @Override
    public String updateCard(CardRequest cardRequest, String userName) {
        var card = cardsRepo.findByIdentifier(cardRequest.getCardId()).orElse(null);
        if(card == null){
            throw new RuntimeException("NON_EXISTENT_CARD");
        }
        card.setName(cardRequest.getCardHolderName());
        card.setNumber(Long.parseLong(cardRequest.getCardNumber().replaceAll("\\s+","")));
        card.setExpireDate(formatToLocalDate(cardRequest.getExpireDate()));
        card.setCvc(cardRequest.getCvc());
        card.setType(cardRequest.getCardType());
        cardsRepo.save(card);
        return "Card updated successfully";
    }

    @Override
    public String SetDefaultCard(String cardId) {
var card = cardsRepo.findByIdentifier(cardId).orElse(null);
        if(card == null){
            throw new RuntimeException("NON_EXISTENT_CARD");
        }
        var customer = card.getCustomer();
        var cards = customer.getPaymentCards();
        cards.forEach(card1 -> {
            if(card1.getIsDefault()){
                card1.setIsDefault(false);
            }
        });
        card.setIsDefault(true);
        cardsRepo.save(card);
        return "Default card set successfully";
    }

    @Override
    public CardResponse getCard(String cardId) {
        var card = cardsRepo.findByIdentifier(cardId).orElse(null);
        if(card == null){
            throw new RuntimeException("NON_EXISTENT_CARD");
        }
        return CardResponse.builder()
                .cardId(card.getIdentifier())
                .cardNumber( (card.getNumber().toString().replaceAll("(.{4})", "$1 ")))
                .cardHolderName(card.getName())
                .cardType(card.getType())
                .expireDate(formatToDateString(card.getExpireDate()))
                .defaultCard(card.getIsDefault())
                .build();
    }
    private String formatToDateString(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern("MM/yy"));
    }
}
