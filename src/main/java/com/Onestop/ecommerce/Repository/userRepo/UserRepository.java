package com.Onestop.ecommerce.Repository.userRepo;



import com.Onestop.ecommerce.Entity.user.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<userEntity, Long> {

  Optional<userEntity> findByEmail(String email);
}
