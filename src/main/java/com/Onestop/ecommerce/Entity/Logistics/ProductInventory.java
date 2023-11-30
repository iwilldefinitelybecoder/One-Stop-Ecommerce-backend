package com.Onestop.ecommerce.Entity.Logistics;


import com.Onestop.ecommerce.Entity.products.Product;

import com.Onestop.ecommerce.Entity.vendor.Vendor;
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
@Table(name = "inventory")
public class ProductInventory {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "wareHouse_id")
    private WareHouse wareHouse;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    private Date inDate;

    private Integer Stock;
    private String identifier;

    @PrePersist
    public void prePersist(){
        this.inDate = new Date();
        this.identifier = UUID.randomUUID().toString();
    }

//    @PreUpdate
//    public void preUpdate(){
//        this.inDate = new Date();
//    }

    // Getters and setters
}
