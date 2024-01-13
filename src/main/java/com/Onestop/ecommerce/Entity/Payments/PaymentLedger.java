package com.Onestop.ecommerce.Entity.Payments;

import com.Onestop.ecommerce.Entity.Customer.Customer;
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
public class PaymentLedger {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

@Column(name = "transaction_id")
    private String transactionId;

@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "transaction_date")
    private Date transactionDate;

@ManyToOne
@JoinColumn(name = "customer_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_customer_id"))
private Customer customer;

private PaymentMethods paymentMethod;
private double amount;
private PaymentStauts status;
private String description;
private String identifier;
private double walletBalance;

@PrePersist
    public void prePersist(){
        this.transactionId = "ONES-TP"+ new Date() + this.paymentMethod.toString();
        this.identifier  = UUID.randomUUID().toString();
    }

}
