package com.Onestop.ecommerce.Entity.card;

import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "card_info")
public class CardInfo {
    @Id
    @Column(name = "card_id")
    private String cardId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    private String name;

    @Column(precision = 12)
    private Long number;

    @Column(name = "expire_date")
    private Integer expireDate;

    // Getters and setters
}
