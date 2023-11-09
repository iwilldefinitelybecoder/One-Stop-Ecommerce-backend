package com.Onestop.ecommerce.Repository.userRepo;

import com.Onestop.ecommerce.Entity.user.UserProfileIcon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileIconRepo extends JpaRepository<UserProfileIcon, Long> {
    Optional<UserProfileIcon> findByUserEntityId(Long id);
}
