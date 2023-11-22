package com.Onestop.ecommerce.Entity.products;


import com.Onestop.ecommerce.Entity.Logistics.ProductInventory;
import com.Onestop.ecommerce.Entity.Logistics.WareHouse;
import com.Onestop.ecommerce.Entity.user.userEntity;
import com.Onestop.ecommerce.Entity.vendor.Vendor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products",indexes = @Index(name = "identifier_index",columnList = "identifier,id",unique = true))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String category;
    private int stock;
    private double regularPrice;
    private double salePrice = 0;
    private boolean enabled = false;
    private boolean published = false;

    @ManyToOne(fetch = FetchType.EAGER)
    private WareHouse wareHouse;
    private String identifier;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_vendor_id"))
    private Vendor vendor;
    @ElementCollection
    private List<String>productTypeTags;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<resourceDetails> images = new ArrayList<>();


    @PrePersist
    public void prePersist() {
        if (this.identifier == null) {
            this.identifier = java.util.UUID.randomUUID().toString();
        }
    }
}
