package htwberlin.backend.controller;

import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.Entity.UserEntity;
import htwberlin.backend.repository.ChatMessageRepository;
import htwberlin.backend.service.ChatMessageService;
import htwberlin.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;
    @Autowired
    private final ChatMessageRepository chatMessageRepository;

    private List<ChatMessageEntity> messages = new ArrayList<>();

    @GetMapping("/message")
    public List<ChatMessageEntity> getChatMessageEntity() {
        return chatMessageRepository.findAll();
    }

    @PostMapping("/message")
    public void addMessage(@RequestBody ChatMessageEntity message) {
        chatMessageService.saveChatMessage(message.getUserName(), message.getMessage());
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        try {
            userService.addUser(user.getUsername(), user.getPassword(), user.getEmail());
            return ResponseEntity.ok().body("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}