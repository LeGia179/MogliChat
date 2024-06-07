package htwberlin.backend.controller;

import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.repository.ChatMessageRepository;
import htwberlin.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
}