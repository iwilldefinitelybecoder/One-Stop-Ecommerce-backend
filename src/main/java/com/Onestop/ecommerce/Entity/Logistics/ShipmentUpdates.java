package com.Onestop.ecommerce.Entity.Logistics;

import com.Onestop.ecommerce.Entity.orders.OrderStatus;
import com.Onestop.ecommerce.Entity.orders.Orders;
import com.Onestop.ecommerce.Entity.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  ShipmentUpdates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String trackingNumber;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(EnumType.STRING)
    private ShipmentAction action;
    private String location;
    private String details;
    private OrderStatus Status;
    @ManyToOne(fetch = FetchType.LAZY)
    private OrderShippment orderShippment;


    @PrePersist
    public void prePersist(){
        this.trackingNumber = orderShippment.getTrackingNumber();
    }

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product;


}