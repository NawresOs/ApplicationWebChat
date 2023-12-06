package com.nawres.chat.conifg;

import com.nawres.chat.chat.ChatMessage;
import com.nawres.chat.chat.MessageType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
@Builder
public class WebSocketEventListener {
    private  final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void HandleWebSocketDisconectListener(
            SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor=StompHeaderAccessor.wrap(event.getMessage());
        String username=(String) headerAccessor.getSessionAttributes().get("username");

        if (username !=null){
            log.info("User disconnected: {}", username);
            var chatMessage= ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
