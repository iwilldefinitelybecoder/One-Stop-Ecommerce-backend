package com.Onestop.ecommerce.Entity.vendor;


import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "vendor",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String email;

    @OneToOne
    @JoinColumn(name = "user_id")
    private userEntity user;

    @Column(name = "vendorName")
    private String vendorCompanyName;

    // Getters and setters
}
