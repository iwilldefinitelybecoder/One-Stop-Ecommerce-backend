package com.Onestop.ecommerce.Entity.Customer;

import com.Onestop.ecommerce.Entity.Cupon.Coupons;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Customer.cart.Cart;
import com.Onestop.ecommerce.Entity.Customer.cart.WishList;
import com.Onestop.ecommerce.Entity.orders.Orders;
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
import java.util.List;

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
    private Collection<Cards> paymentCards = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @OneToMany
    @JoinColumn(name = "order_id")
    private Collection<Orders> orders = new ArrayList<>();

    @JsonIgnoreProperties("customer")
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
    private Cart cart ;

    @JsonIgnoreProperties("customer")
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "customer")
    private WishList wishList;

    @ManyToMany
    @JoinTable(
            name = "customer_coupons",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "coupons_id"))
    private List<Coupons> coupons = new ArrayList<>();

}
