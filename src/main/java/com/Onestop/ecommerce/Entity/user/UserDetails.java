package com.Onestop.ecommerce.Entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetails {
    private String userId;
    private WebSocketSession webSocketSession;
}
