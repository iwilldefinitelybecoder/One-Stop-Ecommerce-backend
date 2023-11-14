package com.Onestop.ecommerce.Entity.UserMessages;

import com.Onestop.ecommerce.Entity.user.userEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_message")
public class UserMessages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private String application;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "email", referencedColumnName = "email")
    private userEntity user;
}
