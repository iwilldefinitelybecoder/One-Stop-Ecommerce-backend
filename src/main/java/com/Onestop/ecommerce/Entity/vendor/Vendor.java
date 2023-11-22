package com.Onestop.ecommerce.Entity.vendor;


import com.Onestop.ecommerce.Entity.products.Product;
import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vendor")
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String supportEmail;

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @Column(name = "vendorName")
    private String vendorCompanyName;

    @OneToMany
    @JoinColumn(name = "product_id")
    private Collection<Product> product = new ArrayList<>();

    // Getters and setters
}
