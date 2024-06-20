package htwberlin.backend.controller;

import htwberlin.backend.Entity.ChatChannelEntity;
import htwberlin.backend.Entity.ChatMessageEntity;
import htwberlin.backend.Entity.UserEntity;
import htwberlin.backend.repository.ChatChannelRepository;
import htwberlin.backend.repository.ChatMessageRepository;
import htwberlin.backend.service.ChatChannelService;
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

@RestController  //Deklariert diese Klasse als RestController
@RequestMapping //legt Basis-URL f端r diese Klasse fest
@CrossOrigin(origins = "*") //erlaubt alle CO-Anfragen
@RequiredArgsConstructor //implementiert Konstruktor f端r alle finals
public class MogliChatController {
    //handled Chatnachrichten
    private final ChatMessageService chatMessageService;
    //handled User
    private final UserService userService;
    @Autowired //chatMessageRepository bean injection
    //Zugriff auf Datenbank
    private final ChatMessageRepository chatMessageRepository;

    private final ChatChannelRepository chatChannelRepository;

    private final ChatChannelService chatChannelService;
    //Liste f端r Nachrichten
    private List<ChatMessageEntity> messages = new ArrayList<>();


    //GET-Endpunkt zum Abrufen aller Chat-Nachrichten aus DB
    @GetMapping("/message")
    public List<ChatMessageEntity> getChatMessageEntity() {
        return chatMessageRepository.findAll();
    }

    //POST-Endpunkt zum Hinzuf端gen einer neuen Nachricht
    @PostMapping("/message")
    public void addMessage(@RequestBody ChatMessageEntity message) {//nimmt Daten aus Anfragetext
        chatMessageService.saveChatMessage(message.getUserName(), message.getMessage());
    }

    //POST-Endpunkt zum Registrieren eines neuen Benutzers
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        try {
            userService.addUser(user.getUsername(), user.getPassword(), user.getEmail());
            return ResponseEntity.ok().body("User created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    //POST-Endpunkt zum Anmelden eines Benutzers
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        try {
            UserEntity authenticatedUser = userService.authenticateUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(authenticatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/messages")
    public ResponseEntity<?> deleteChatMessage() {
        chatMessageRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/channel/{userName}")
    public ResponseEntity<?> createChannel(@RequestBody ChatChannelEntity chatChannel, @PathVariable("userName") String userName) {
        chatChannelService.createChannel(chatChannel.getName(), userName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Channel created successfully");
    }
}