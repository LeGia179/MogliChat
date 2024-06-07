package htwberlin.backend.controller;

import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MogliChatController {
    private final ChatMessageService chatMessageService;

    private List<ChatMessageEntity> messages = new ArrayList<>();
    @GetMapping("/chatP2P")
    public List<ChatMessageEntity> getChatMessageEntity() {
        return messages;
    }
    @PostMapping("/chatP2P")
    public ResponseEntity<Void> addChatMessage(@RequestBody ChatMessageEntity message) {
        messages.add(message);
        message.setTimestamp(LocalDateTime.now());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/message")
    public void addMessage(@RequestBody ChatMessageEntity message) {
        chatMessageService.saveChatMessage(message.getUserName(), message.getMessage());
    }
    @GetMapping("/mogli")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }
}