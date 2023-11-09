package com.Onestop.ecommerce.Entity.cart;



import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "Cart_id")
    private String cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;

    // Getters and setters
}
