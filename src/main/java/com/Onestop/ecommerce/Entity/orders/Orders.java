package com.Onestop.ecommerce.Entity.orders;


import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.Payments.PaymentMethods;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String trackingId;
    private OrderStatus status;
    private String generatedOrderId;

    @Column(name = "payment_status")
    private boolean paymentStatus;

    private double total;
    private double shippingTotal;
    private double taxTotal;
    private Long grandTotal;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @OneToOne
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_items_id")
    private Collection<OrderItems> orderItems = new ArrayList<>();


    @Transient
    @OneToOne
    @JoinColumn(name = "payment_id")
    private Cards paymentCard;

    private PaymentMethods paymentMethod;
    private String identifier;
    private boolean returnable = true;

    @Temporal(TemporalType.TIMESTAMP)
    private Date DeliveredDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ExpectedDeliveryDate;

    @Column(name = "order_date")
    @OrderBy("orderDate DESC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "ware_house_id")
    private WareHouse wareHouse;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    private Date ReplacementLastDate;

    @PrePersist
    public void prePersist(){
        this.orderDate = new Date();
        if(identifier == null){
            this.identifier = UUID.randomUUID().toString();
        }
        orderSummary();
    }

    @PreUpdate
    public void preUpdate(){
        if(this.DeliveredDate != null){
            this.ReplacementLastDate = new Date(this.DeliveredDate.getTime() + 7*24*60*60*1000);
        }
        if(this.paymentMethod == PaymentMethods.COD){
            this.paymentStatus = false;
        }

    }

    private void orderSummary(){
        double taxRate = 0.1;
        double shippingRate = 0.05;
        this.shippingTotal = (long) (total * shippingRate);
        this.taxTotal = (long) (total * taxRate);


        this.grandTotal = (long) (total + shippingTotal + taxTotal);
    }

    // Getters and setters
}
