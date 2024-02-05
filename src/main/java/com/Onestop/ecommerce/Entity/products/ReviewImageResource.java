package com.Onestop.ecommerce.Entity.products;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewImageResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String url;
    private String downSizedUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_review_id"))
    private Review review;
}
