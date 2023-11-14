package com.Onestop.ecommerce.Repository.MessageRepo;

import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMessagesRepo extends JpaRepository<UserMessages,Long> {
    Optional<UserMessages> findByUserEmailAndStatus(String email, MessageStatus status);
}
