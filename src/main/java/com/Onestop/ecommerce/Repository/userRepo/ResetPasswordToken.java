package com.Onestop.ecommerce.Repository.userRepo;

import com.Onestop.ecommerce.Entity.user.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordToken extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByToken(String token);
    Optional<PasswordResetToken> findByUserEmail(String email);
    void deleteByToken(String token);
}
