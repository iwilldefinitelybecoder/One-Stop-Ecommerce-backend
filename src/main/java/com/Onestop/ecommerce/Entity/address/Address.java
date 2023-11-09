package com.Onestop.ecommerce.Entity.address;


import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
    public class Address {
        @Id
        @Column(name = "address_id")
        private String addressId;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private userEntity user;

        @Column(name = "street_address", length = 300)
        private String streetAddress;

        @Column(length = 300)
        private String city;

        @Column(length = 50)
        private String state;

        @Column(name = "postal_code", precision = 10)
        private Long postalCode;

        @Column(length = 20)
        private String country;

        // Getters and setters
    }