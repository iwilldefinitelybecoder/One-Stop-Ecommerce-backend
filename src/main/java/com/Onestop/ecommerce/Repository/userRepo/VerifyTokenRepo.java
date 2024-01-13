package com.Onestop.ecommerce.Repository.userRepo;

import com.Onestop.ecommerce.Entity.user.VerifyEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyTokenRepo extends JpaRepository<VerifyEmailToken,Long> {

    Optional<VerifyEmailToken> findByToken(String token);

}
