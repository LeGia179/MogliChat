package htwberlin.backend.controller;

import htwberlin.backend.Entity.ChatMessageEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class MogliChatController {
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

    @GetMapping("/mogli")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.ok("Hello World");
    }
}