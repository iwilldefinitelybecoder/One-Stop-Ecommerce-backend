package com.Onestop.ecommerce.Entity.orders;


import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private String identifier;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    private Double itemTotal;
    private Double itemPrice;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;



    @PrePersist
    public void prePersist(){
        this.identifier = product.getIdentifier();
    }


}
