package com.Onestop.ecommerce.Entity.products;

import com.Onestop.ecommerce.Entity.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    private String review;
    private int rating;
    private Date date;

}
