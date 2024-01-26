package com.Onestop.ecommerce.Entity.Customer;

import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  UserPurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OrderBy("customer_id DESC")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private double total;
    private double price;
    private String identifier;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @PrePersist
    public void prePersist(){
        this.identifier = UUID.randomUUID().toString();
        this.price = this.product.getSalePrice() != 0 ? this.product.getSalePrice() :this.product.getRegularPrice();
        this.total = this.quantity * this.price;
    }

}
