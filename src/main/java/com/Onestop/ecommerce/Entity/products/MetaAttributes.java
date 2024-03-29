package com.Onestop.ecommerce.Entity.products;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetaAttributes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_product_id"))
    private Product product;
    @Enumerated(EnumType.STRING)
    private MetaAttribute attributes;
    private boolean isActive;

}
