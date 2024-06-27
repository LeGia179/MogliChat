package htwberlin.backend.WebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.service.ChatMessageService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatMessageService chatMessageService;
    private final List<WebSocketSession> sessions = new ArrayList<>();
    public WebSocketHandler(ObjectMapper objectMapper, ChatMessageService chatMessageService) {
        this.objectMapper = objectMapper;
        this.chatMessageService = chatMessageService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        ChatMessageEntity chatMessage = objectMapper.readValue(payload, ChatMessageEntity.class);
        chatMessageService.saveMessage(chatMessage);
        broadcastMessage(chatMessage);
    }
    private void broadcastMessage(ChatMessageEntity message) throws IOException {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("WebSocket connection closed");
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket transport error: " + exception.getMessage());
    }
}

