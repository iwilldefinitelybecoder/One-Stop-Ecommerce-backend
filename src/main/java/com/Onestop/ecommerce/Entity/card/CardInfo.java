package com.Onestop.ecommerce.Entity.card;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
@Table(name = "card_info")
public class CardInfo {
    @Id
    @Column(name = "card_id")
    private String cardId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String name;

    @Column(precision = 12)
    private Long number;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    // Getters and setters
}
