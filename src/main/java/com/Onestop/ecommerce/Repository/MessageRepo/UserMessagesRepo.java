package com.Onestop.ecommerce.Repository.MessageRepo;

import com.Onestop.ecommerce.Entity.UserMessages.MessageStatus;
import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMessagesRepo extends JpaRepository<UserMessages,Long> {
    Optional<UserMessages> findByUserEmailAndStatus(String email, MessageStatus status);
    Optional<List<UserMessages>> findAllByUserEmail(String email);
    Optional<List<UserMessages>> findAllByStatus(MessageStatus status);
    Optional<List<UserMessages>> findAllByStatusAndUserEmail(MessageStatus status,String email);
    Optional<UserMessages> findByIdentifier(String identifier);
    Optional<List<UserMessages>> findAllByUserEmailAndStatus(String email,MessageStatus status);
    void deleteByIdentifier(String identifier);
    void deleteAllByUserEmail(String email);

}
