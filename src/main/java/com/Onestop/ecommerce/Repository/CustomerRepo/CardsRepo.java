package com.Onestop.ecommerce.Repository.CustomerRepo;

import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepo extends JpaRepository<Cards, Long> {

    Optional<Cards> findByIdentifier(String identifier);
     Optional<Cards> findByNumber(Long number);
}
