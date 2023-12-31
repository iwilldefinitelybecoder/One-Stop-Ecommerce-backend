package com.Onestop.ecommerce.Entity.Customer.address;


import com.Onestop.ecommerce.Entity.Customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address",indexes = {@Index(name = "id_Identifier",columnList = "address_id,identifier")})
    public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "address_id")
        private Long addressId;

        @ManyToOne
        @JoinColumn(name = "customer_id")
        private Customer customer;

        @Column(length = 300)
        private String locality;

        private String name;
        private String email;
        private String phone;

        @Column(length = 300)
        private String city;


        @Column(name = "street", length = 300)
        private String area;

        @Column(name = "zip_code", length = 10)
        private int zipCode;

        @Column(length = 20)
        private String country;

        private String identifier;

        @PrePersist
        public void prePersist() {
            if (this.identifier == null) {
                this.identifier = UUID.randomUUID().toString();
            }
        }

        // Getters and setters
    }