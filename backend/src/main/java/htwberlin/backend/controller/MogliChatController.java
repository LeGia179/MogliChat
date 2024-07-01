package htwberlin.backend.controller;

import htwberlin.backend.WebSocketTransfer.ChatTransfer;
import htwberlin.backend.WebSocketTransfer.MessageTransfer;
import htwberlin.backend.Entity.Message;
import htwberlin.backend.Entity.Textchannel;
import htwberlin.backend.Entity.User;
import htwberlin.backend.service.ChatMessageService;

import htwberlin.backend.service.MultichannelService;
import htwberlin.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MogliChatController {

    private final ChatMessageService messageService;
    private final UserService userService;
    private final MultichannelService multichannelService;


    //Erstellt ein neuen Benutzer
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User created_user = userService.createUser(user.getUsername(), user.getEmail(), user.getPassword());
        if (created_user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Benutzer existiert bereits.");
        }
        return ResponseEntity.ok(created_user);
    }
    //Benutzer anmelden
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    //Erstellt ein neuen Kanal
    @PostMapping("/channels/users/{userId}")
    public ResponseEntity<?> createTextchannel(@RequestBody Textchannel textchannel,
                                               @PathVariable("userId") String userId){
        Textchannel created_textchannel = multichannelService.createTextchannel(textchannel.getName(), textchannel.getDescription(), userId);
        if (created_textchannel == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Kanal existiert bereits.");
        }
        return ResponseEntity.ok(created_textchannel);
    }

    //Benutzer zum Kanal hinzufügen
    @PostMapping("/channels/{channelId}/users/{userId}")
    public void addUserToChannel(@PathVariable("channelId") String channelId,
                                 @PathVariable("userId") String userId){
        multichannelService.addUserToTextchannel(userId, channelId);
    }

    //Nachrichten zum Kanal hinzufügen
    @PostMapping("/channels/{channelId}/users/{userId}/messages")
    public ResponseEntity<?> addMessageToChannel(@RequestBody Message message_input,
                                                 @PathVariable("channelId") String channelId,
                                                 @PathVariable("userId") String userId){
        Message message = messageService.createMessage( userId,
                                                        channelId,
                                                        message_input.getContent());
        multichannelService.addMessageToTextchannel(message, channelId);

        ChatTransfer chatTransfer = new ChatTransfer();
        chatTransfer.setContent(message.getContent());
        chatTransfer.setSender(message.getSender().getUsername());
        chatTransfer.setDate(message.getDate());
        return ResponseEntity.ok(chatTransfer);
    }

    //Benutzer zum Kanal mit Kanalnamen hinzufügen
    @PostMapping("/channels/name/{channelName}/users/{userId}")
    public void addUserToChannelByName(@PathVariable("channelName") String channelName, @PathVariable("userId") String userId){
        multichannelService.addUserToChannelByName(channelName, userId);
    }

    //Nachrichten erhalt -> Kanal und Benutzer
    @GetMapping("/channels/{channelId}/users/{userId}/messages")
    public List<Message> getMessagesByChannelIdAndUserId(@PathVariable("channelId") String channelId,
                                                         @PathVariable("userId") String userId) {
        return messageService.getMessagesByTextchannelAndUser(channelId, userId);
    }

    //Alle Nachrichten mit KanalId
    @GetMapping("/channels/{channelId}/messages")
    public List<MessageTransfer> getMessagesByChannelId(@PathVariable("channelId") String channelId){
        return messageService.getMessagesByTextchannel(channelId);
    }
    //Alle Nachrichten mit BenutzerId
    @GetMapping("/users/{userId}/channels")
    public List<Textchannel> getChannelsByUserId(@PathVariable("userId") String userId){
        return multichannelService.getAllTextchannelsByUserId(userId);
    }

    //Benutzer aus KanalId
    @GetMapping("/channels/{channelId}/users")
    public List<User> getUsersOfTextChannel(@PathVariable("channelId") String channelId){
        return multichannelService.getUsersOfTextChannel(channelId);
    }
    //Benutzer von BenutzerId
    @GetMapping("/users/{userId}")
    public User getUsersById(@PathVariable("userId") String userId){

        return userService.getUserById(userId);
    }

    //Kanal von KanalId
    @GetMapping("/channels/{channelId}")
    public Textchannel getTextchannelById(@PathVariable("channelId") String channelId){
        return multichannelService.getTextchannelById(channelId);
    }
    //Kanal nach Namen abrufen
    @GetMapping("/channels/name/{name}")
    public Textchannel getTextchannelByName(@PathVariable("name") String name){
        return multichannelService.getTextchannelByName(name);
    }

    //Benutzer abrufen anhand Benutzername
    @GetMapping("/users/username/{username}")
    public User getUserById(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }

    //Benutzer abrufen anhand Email
    @GetMapping("/users/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User foundUser = userService.getUserByEmail(email);
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(foundUser);
    }

    //Benutzer von kanal löschen
    @DeleteMapping("/channels/{channelId}/users/{userId}")
    public ResponseEntity<?> removeUserFromChannel(@PathVariable("channelId") String channelId, @PathVariable("userId") String userId) {
        multichannelService.removeUserFromTextchannel(userId, channelId);
        return ResponseEntity.ok("Benutzer mit der ID " + userId + " wurde aus dem Kanal mit der ID: " + channelId + " gelöscht.");
    }

    //Löscht alle Nachrichten aus ein Kanal
    @DeleteMapping("/textchannels/delete-all-messages")
    public ResponseEntity<?> deleteAllMessagesFromTextchannels() {
        multichannelService.deleteAllMessagesFromTextchannels();
        userService.deleteAllMessagesFromUsers();
        messageService.deleteAllMessages();
        userService.deleteAllUsers();
        multichannelService.deleteAllTextchannels();
        return ResponseEntity.ok("Alle Nachrichten aus dem Kanal und Benutzer wurden gelöscht!");
    }

    //Löscht Benutzer durch Email
    @DeleteMapping("/users/email")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam("email") String email) {
        User foundUser = userService.getUserByEmail(email);
        if (foundUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("Benutzer mit der Email: " + email + " wurde gelöscht.");
    }

    //Löscht Benutzer durch BenutzerId
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") String userId) {
        User foundUser = userService.getUserById(userId);
        if (foundUser == null) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        userService.deleteUserById(userId);
        return ResponseEntity.ok("Benutzer mit der ID: " + userId + " wurde gelöscht!");
    }

    //Löscht Kanal anhand Name
    @DeleteMapping("/channels/name")
    public ResponseEntity<?> deleteTextchannelByName(@RequestParam("name") String name) {
        Textchannel foundTextchannel = multichannelService.getTextchannelByName(name);
        if (foundTextchannel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        multichannelService.deleteTextchannelByName(name);
        return ResponseEntity.ok("Der Kanal mit den namen: " + name + " wurde gelöscht!");
    }

}
