package com.Onestop.ecommerce.Entity.Logistics;

import com.Onestop.ecommerce.Entity.orders.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WareHouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String wareHouseName;
    private String wareHouseLocation;
    private Long contactNumber;
    private Long capacity;
    @OneToMany(cascade = CascadeType.ALL)
    private Collection<ProductInventory> inventory = new ArrayList<>();
    private String identifier;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<Orders> orders = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        this.identifier= UUID.randomUUID().toString();
    }

    @PreUpdate
    public void preUpdate(){
       if(this.capacity <= this.inventory.size()){
              throw new RuntimeException("WareHouse is full");
       }
    }

}


