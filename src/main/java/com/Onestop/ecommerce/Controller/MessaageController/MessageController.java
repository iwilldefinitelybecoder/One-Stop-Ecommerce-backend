package com.Onestop.ecommerce.Controller.MessaageController;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {


//    @GetMapping("/GetAllMessages")
//    public ResponseEntity<?> getAllMessages(){
//        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
////        return ResponseEntity.ok(services.getAllMessages(userName));
//    }
}
