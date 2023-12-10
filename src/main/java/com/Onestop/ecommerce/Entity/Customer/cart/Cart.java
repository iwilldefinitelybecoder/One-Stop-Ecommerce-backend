package com.Onestop.ecommerce.Entity.Customer.cart;



import com.Onestop.ecommerce.Entity.Customer.Customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Items> items = new ArrayList<>();

    private String identifier;
    private  Integer totalItems;

    private double CartTotal;

    private double shippingTotal;
    private double taxTotal;
    private double discountTotal;
    private double subTotal;
    private double grandTotal;

    @PrePersist
    public void performPersistActions(){
        uniqueIdentifier();
        performPreUpdateActions();
    }
    @PreUpdate
    public void performPreUpdateActions(){
        updateTotalItems();
        updateCartTotal();
    }

    private void uniqueIdentifier(){
        this.identifier = UUID.randomUUID().toString();
    }


    private void updateTotalItems(){
        if(this.items == null){
            return;
        }
        this.totalItems = this.items.size();
    }

    private void updateCartTotal(){
        if(this.items == null){
            return;
        }
        this.CartTotal = this.items.stream().mapToDouble(Items::getTotalPrice).sum();
    }






    // Getters and setters
}
