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
import java.util.Collection;
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
    @Column(length = 1000)
    private String description;
    private String category;
    private Integer stock;
    private double regularPrice;
    private double salePrice = 0;
    private boolean enabled = false;
    private boolean published = false;
    private String brand;
    private double averageRating;

    @ManyToOne(fetch = FetchType.EAGER)
    private WareHouse wareHouse;
    private String identifier;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_vendor_id"))
    private Vendor vendor;
    @OneToOne
    private resourceDetails thumbnail;
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Tags> tags = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<resourceDetails> images = new ArrayList<>();

    @OneToMany
    private Collection<Review> reviews = new ArrayList<>();
    private String extraAttributesId;

  @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductInventory productInventory;




    @PrePersist
    public void prePersist() {
        if (this.identifier == null) {
            this.identifier = java.util.UUID.randomUUID().toString();
        }
    }

    @PreUpdate
    public void preUpdate() {
       AverageRating();
    }
    private void AverageRating() {
        List<Review> reviews = (List<Review>) this.getReviews();;
        if (reviews.isEmpty()) {
            this.averageRating = 0.0;
        }

        double totalRating = reviews.stream().mapToInt(Review::getRating).sum();
        this.averageRating =  totalRating / reviews.size();
    }
}
