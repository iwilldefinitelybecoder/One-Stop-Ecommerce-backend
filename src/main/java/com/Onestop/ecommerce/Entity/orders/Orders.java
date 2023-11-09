package com.Onestop.ecommerce.Entity.orders;


import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;

@Entity
@Data
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private String status;

    @Column(name = "payment_status")
    private boolean paymentStatus;

    // Getters and setters
}
