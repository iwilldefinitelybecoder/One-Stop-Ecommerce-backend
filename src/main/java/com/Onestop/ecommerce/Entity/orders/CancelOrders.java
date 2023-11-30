package com.Onestop.ecommerce.Entity.orders;

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
public class CancelOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "order_items_id")
    private OrderItems orderItems;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders orders;

    private String reason;
    private String description;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String identifier;

    @PrePersist
    public void prePersist() {
        if (identifier == null) {
            this.identifier = UUID.randomUUID().toString();
        }
    }
}
