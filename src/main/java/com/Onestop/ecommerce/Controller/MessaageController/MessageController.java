package com.Onestop.ecommerce.Controller.MessaageController;

import com.Onestop.ecommerce.Entity.UserMessages.UserMessages;
import com.Onestop.ecommerce.Service.MessageService.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;



    @GetMapping("/getAll")
    public ResponseEntity<?> getAllMessages(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            return ResponseEntity.status(HttpStatus.OK).body(messageService.getAllMessages(userEmail,pageable));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(@RequestParam("identifier") String identifier) {
        try {
            messageService.deleteMessage(identifier);
            return ResponseEntity.status(HttpStatus.OK).body("Message deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllMessages() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            messageService.deleteAllMessages(userEmail);
            return ResponseEntity.status(HttpStatus.OK).body("All messages deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateMessage(@RequestBody List<String> messageId) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.updateMessage(messageId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Additional methods (get, getByIdentifier, etc.) can be added based on your requirements
}

