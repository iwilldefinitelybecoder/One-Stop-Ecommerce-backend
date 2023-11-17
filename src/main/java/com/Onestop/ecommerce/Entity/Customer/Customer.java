package com.Onestop.ecommerce.Entity.Customer;

import com.Onestop.ecommerce.Entity.address.Address;
import com.Onestop.ecommerce.Entity.card.CardInfo;
import com.Onestop.ecommerce.Entity.cart.Cart;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1546145213126326L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany( cascade = CascadeType.ALL)
    private Collection<Address> address = new ArrayList<>();

    @OneToMany( cascade = CascadeType.ALL)
    private Collection<CardInfo> paymentCards = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @JsonIgnoreProperties("customer")
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
    private Cart cart ;
}
