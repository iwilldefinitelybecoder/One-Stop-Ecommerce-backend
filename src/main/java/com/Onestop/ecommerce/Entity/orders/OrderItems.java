package com.Onestop.ecommerce.Entity.orders;


import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String identifier;

    private Long quantity;

    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    private Double itemTotal;
    private Double itemPrice;


    @PrePersist
    public void prePersist(){
        this.identifier = product.getIdentifier();
    }


}
