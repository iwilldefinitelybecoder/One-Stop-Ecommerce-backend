package com.Onestop.ecommerce.Entity.Customer.cart;

import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Entity.products.ProductAvaiablity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "items",indexes = @Index(name = "identifier_index",columnList = "identifier,id",unique = true))
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private String identifier;
    private double totalPrice;

    private ProductAvaiablity status;


    @PrePersist
    public void performPersistActions(){
        uniqueIdentifier();
        performPreUpdateActions();
    }

    @PreUpdate
    public void performPreUpdateActions(){
       ItemTotal();
    }

    private void uniqueIdentifier(){
        if (this.identifier == null) {
            this.identifier = UUID.randomUUID().toString();
        }
    }



    private void ItemTotal(){
        if(this.product != null){
            this.totalPrice = this.quantity * this.product.getSalePrice();
        }
    }





}
