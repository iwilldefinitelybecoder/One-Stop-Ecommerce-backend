package com.Onestop.ecommerce.Entity.products;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import com.Onestop.ecommerce.Entity.Customer.UserPurchaseHistory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_product_id"))
    private Product product;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_customer_id"))
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "user_purchase_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_purchase_id"))
    private UserPurchaseHistory userPurchaseHistory;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImageResource> images = new ArrayList<>();

    private String review;
    private int rating;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String headline;

}
