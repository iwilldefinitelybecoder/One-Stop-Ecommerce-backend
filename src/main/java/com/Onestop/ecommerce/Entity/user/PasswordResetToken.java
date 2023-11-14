package com.Onestop.ecommerce.Entity.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordResetToken {
    private static final int EXPIRATION = 10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expiryDate;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false,foreignKey = @ForeignKey(name = "FK_VERIFY_EMAIL_TOKEN"))
    private userEntity user;

    public PasswordResetToken(String token, userEntity user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    public PasswordResetToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiration) {
        var cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(java.util.Calendar.MINUTE, expiration);
        return new Date(cal.getTime().getTime());
    }
}
