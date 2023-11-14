package com.Onestop.ecommerce.Entity.products;


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
@Table(name = "products" )
public class Product {

     @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
    private String name;
    private String description;
    private String category;
    private int stock;
    private double regularPrice;
    private double salePrice = 0;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_vendor_id"))
    private Vendor vendor;
    @ElementCollection
    private List<String>productTypeTags;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<resourceDetails> images = new ArrayList<>();
}
