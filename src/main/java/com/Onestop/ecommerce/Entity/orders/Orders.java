package com.Onestop.ecommerce.Entity.orders;


import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.address.Address;
import com.Onestop.ecommerce.Entity.Customer.card.Cards;
import com.Onestop.ecommerce.Entity.Logistics.ShipmentMethod;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.Payments.PaymentMethods;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Orders {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String trackingId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String generatedOrderId;


    @Column(name = "payment_status")
    private boolean paymentStatus;

    private double total;
    private double shippingTotal;
    private double taxTotal;
    private double grandTotal;
    private Double discount;
    private String couponCode;


    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<OrderItems> orderItems = new ArrayList<>();



    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Cards paymentCard;
    @Enumerated(EnumType.STRING)
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
    @JoinColumn(name = "ware_house_id",unique = false)
    private WareHouse wareHouse;


    @Temporal(TemporalType.TIMESTAMP)
    private Date ReplacementLastDate;

    @Enumerated(EnumType.STRING)
    private ShipmentMethod shipmentMethod;

    @PrePersist
    public void prePersist(){
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

    private void orderSummary() {
        if (this.grandTotal == 0) {
            double taxRate = 0.02;
            double shippingRate = 0.03;
            if(this.shipmentMethod.equals(ShipmentMethod.EXPRESS)){
                shippingRate = shippingTotal + 300;
            }else if(this.shipmentMethod.equals(ShipmentMethod.STANDARD)){
                shippingRate = shippingTotal + 100;
            }
            this.shippingTotal = (long) (total * shippingRate);
            this.taxTotal = (long) (total * taxRate);


            this.grandTotal = (long) (total + shippingTotal + taxTotal);
        }
    }



}
