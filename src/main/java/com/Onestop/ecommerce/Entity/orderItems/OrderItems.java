package com.Onestop.ecommerce.Entity.orderItems;


import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Entity.orders.Orders;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    // Getters and setters
}
