package com.Onestop.ecommerce.Entity.vendor;

import com.Onestop.ecommerce.Entity.products.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OrderBy("vendor_id DESC")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "salesData", cascade = CascadeType.ALL)
    private List<SalesHistory> salesHistory = new ArrayList<>();

    private double revenue;
    private int productSold;
    private String identifier;

    @PrePersist
    public void prePersist(){
        this.identifier = UUID.randomUUID().toString();
    }
}
