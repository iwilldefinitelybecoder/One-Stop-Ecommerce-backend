package com.Onestop.ecommerce.Entity.Logistics;

import com.Onestop.ecommerce.Entity.orders.OrderItems;
import com.Onestop.ecommerce.Entity.orders.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderShippment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String trackingNumber;
    private String carrier;
    private ShipmentMethod shippingMethod;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String OrderStatus;

    @OneToMany(mappedBy = "orderShippment", cascade = CascadeType.ALL)
    private List<ShipmentUpdates> shipmentUpdates = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_items_id")
    private OrderItems orderItems;

    @PrePersist
    public void prePersist(){

    }


        public String generateTrackingId() {
            Random random = new Random();
            StringBuilder trackingId = new StringBuilder();
            for (int i = 0; i < 8; i++) {
                trackingId.append(random.nextInt(10)); // Generates a random digit (0-9)
            }
            return trackingId.toString();
        }

}
