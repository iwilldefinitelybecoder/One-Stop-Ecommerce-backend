package com.Onestop.ecommerce.Entity.Customer.card;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "card_info",indexes = {@Index(name = "card_info_index",columnList = "identifier",unique = true)})
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long Id;

    private String identifier;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String name;

    private Long number;

    @Enumerated(EnumType.STRING)
    private CardType type;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(precision = 3)
    private String cvc;

    @PrePersist
    public void prePersist(){
        if(this.identifier == null){
            this.identifier = UUID.randomUUID().toString();
        }
    }
    @CreatedDate
    private LocalDate createdAt;
    @LastModifiedDate
    private LocalDate updatedAt;

}
