package com.Onestop.ecommerce.Entity.Customer.cart;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.products.Product;
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
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Product> product = new ArrayList<>();

    private String identifier;
    private  Integer totalItems;

    @PrePersist
    public void prePersist(){
    if(this.identifier == null){
            this.identifier = UUID.randomUUID().toString();
        }

    }

    @PreUpdate
    public void preUpdate(){
        this.totalItems = this.product.size();
    }



}
