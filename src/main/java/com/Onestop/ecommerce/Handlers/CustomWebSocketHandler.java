package com.Onestop.ecommerce.Handlers;

import com.Onestop.ecommerce.Entity.user.UserDetails;
import com.Onestop.ecommerce.Events.Emmitter.SocketMessage;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomWebSocketHandler.class);
    private final Map<String, UserDetails> userSessionMap = new ConcurrentHashMap<>();

    private final SimpMessagingTemplate messageTemplate;

    @EventListener
    public void handelPrivateMessageEvent(SocketMessage privateMessageEvent){
        String userId = privateMessageEvent.getUserId();
        String message = privateMessageEvent.getMessage();
        String userName = getCurrentUserName();
        if(userName == null){
            logger.info("User with session id {} is not authenticated", userId);
            return;
        }
        if(!userSessionMap.containsKey(userId)){
            logger.info("User with session id {} is not connected",  userId);
            return;
        }
        UserDetails userDetails = userSessionMap.get(userId);
        WebSocketSession webSocketSession = userDetails.getWebSocketSession();
        try{
            messageTemplate.convertAndSendToUser(webSocketSession.getId(), "/queue/notification", message);
        }catch (Exception e){
            logger.error("Error while sending message to user {} with session id {} errorMessage {}", userName, webSocketSession.getId(), e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String sessionId = session.getId();
        String userName = getCurrentUserName();
        UserDetails userDetails = UserDetails.builder().userId(userName).webSocketSession(session).build();
        log.info("User {} connected with session id {}", userName, sessionId);

        if(userName == null){
            logger.info("User with session id {} is not authenticated", sessionId);
            session.close();
            return;
        }
        addUserDetails(userName, userDetails);
        logger.info("User {} connected with session id {}", userName, sessionId);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        String userName = getCurrentUserName();
        removeUserDetails(userName);
        logger.info("User {} disconnected with session id {}", userName, sessionId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String sessionId = session.getId();
        String userName = getCurrentUserName();
        logger.info("User {} sent message with session id {}", userName, sessionId);
    }

    private String getCurrentUserName(){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()){
            return authentication.getName();
        }
        return null;
    }

    public UserDetails getUserDetails(String sessionId) {
        return userSessionMap.get(sessionId);
    }

    public void addUserDetails(String userName, UserDetails userDetails) {
        userSessionMap.put(userName, userDetails);
    }

    public void removeUserDetails(String userName) {
        userSessionMap.remove(userName);
    }


}
