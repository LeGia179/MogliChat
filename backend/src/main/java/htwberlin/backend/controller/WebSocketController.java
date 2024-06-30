package htwberlin.backend.controller;

import htwberlin.backend.WebSocketTransfer.ChatTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
@CrossOrigin(origins = "*")
public class WebSocketController {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @MessageMapping("/chat.sendMessage/{channelId}")
    @SendTo("/topic/channel/{channelId}")
    public ChatTransfer broadcastMessage(@Payload ChatTransfer chatTransfer, @DestinationVariable String channelId) {
        logger.info("Empfange Nachrichten aus dem Kanal {}: {}", channelId, chatTransfer);
        return chatTransfer;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Websocket wurde verbunden!");
    }

    // Handle WebSocket disconnection event
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("Websocket verbindung getrennt!");
    }
}
