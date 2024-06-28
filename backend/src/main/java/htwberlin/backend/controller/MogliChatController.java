package htwberlin.backend.controller;

import htwberlin.backend.WebSocketTransfer.ChatTransfer;
import htwberlin.backend.WebSocketTransfer.MessageTransfer;
import htwberlin.backend.Entity.Directchannel;
import htwberlin.backend.Entity.Message;
import htwberlin.backend.Entity.Textchannel;
import htwberlin.backend.Entity.User;
import htwberlin.backend.service.SinglechannelService;
import htwberlin.backend.service.ChatmessageService;
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

    private final ChatmessageService messageService;
    private final UserService userService;
    private final MultichannelService multichannelService;
    private final SinglechannelService singlechannelService;

    //create new user
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User created_user = userService.createUser(user.getUsername(), user.getEmail(), user.getPassword());
        if (created_user == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User already exists");
        }
        return ResponseEntity.ok(created_user);
    }

    //create new channel
    @PostMapping("/channels/users/{userId}")
    public ResponseEntity<?> createTextchannel(@RequestBody Textchannel textchannel, @PathVariable("userId") String userId){
        Textchannel created_textchannel = multichannelService.createTextchannel(textchannel.getName(), textchannel.getDescription(), userId);
        if (created_textchannel == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Channel already exists");
        }
        return ResponseEntity.ok(created_textchannel);
    }

    //create new direct channel
    @PostMapping("/directchannels")
    public Directchannel createDirectchannel(String userId1, String userId2){
        return singlechannelService.createDirectchannel(userId1, userId2);
    }


    //get user by username
    @GetMapping("/users/username/{username}")
    public User getUserById(@PathVariable("username") String username){
        return userService.getUserByUsername(username);
    }


    //get user by Email
    @GetMapping("/users/email")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email) {
        User foundUser = userService.getUserByEmail(email);
        if (foundUser == null) {
            // User not found, return 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // User found, return 200 OK with the user data
        return ResponseEntity.ok(foundUser);
    }

    //add new user to channel
    @PostMapping("/channels/{channelId}/users/{userId}")
    public void addUserToChannel(@PathVariable("channelId") String channelId, @PathVariable("userId") String userId){
        multichannelService.addUserToTextchannel(userId, channelId);
    }

    //add message to channel
    @PostMapping("/channels/{channelId}/users/{userId}/messages")
    public ResponseEntity<?> addMessageToChannel(@RequestBody Message message_input,
                                    @PathVariable("channelId") String channelId,
                                    @PathVariable("userId") String userId){
        Message message = messageService.createMessage(userId,channelId,message_input.getContent());
        multichannelService.addMessageToTextchannel(message, channelId);

        ChatTransfer chatTransfer = new ChatTransfer();
        chatTransfer.setMessage(message.getContent());
        chatTransfer.setSender(message.getSender().getUsername());
        chatTransfer.setDate(message.getDate());
        return ResponseEntity.ok(chatTransfer);
    }

    //get messages by channelId and userId
    @GetMapping("/channels/{channelId}/users/{userId}/messages")
    public List<Message> getMessagesByChannelIdAndUserId(@PathVariable("channelId") String channelId,
                                                         @PathVariable("userId") String userId) {
        return messageService.getMessagesByTextchannelAndUser(channelId, userId);
    }

    //get all messages from a channel
    @GetMapping("/channels/{channelId}/messages")
    public List<MessageTransfer> getMessagesByChannelId(@PathVariable("channelId") String channelId){
        return messageService.getMessagesByTextchannel(channelId);
    }
    //get all channels by UserId
    @GetMapping("/users/{userId}/channels")
    public List<Textchannel> getChannelsByUserId(@PathVariable("userId") String userId){
        return multichannelService.getAllTextchannelsByUserId(userId);
    }
    //get users by channelId
    @GetMapping("/channels/{channelId}/users")
    public List<User> getUsersOfTextChannel(@PathVariable("channelId") String channelId){
        return multichannelService.getUsersOfTextChannel(channelId);
    }
    //get user by userID
    @GetMapping("/users/{userId}")
    public User getUsersById(@PathVariable("userId") String userId){
        return userService.getUserById(userId);
    }

    //get channel by channelId
    @GetMapping("/channels/{channelId}")
    public Textchannel getTextchannelById(@PathVariable("channelId") String channelId){
        return multichannelService.getTextchannelById(channelId);
    }
    //get textchannel by name
    @GetMapping("/channels/name/{name}")
    public Textchannel getTextchannelByName(@PathVariable("name") String name){
        return multichannelService.getTextchannelByName(name);
    }

    @PostMapping("/channels/name/{channelName}/users/{userId}")
    public void addUserToChannelByName(@PathVariable("channelName") String channelName, @PathVariable("userId") String userId){
        multichannelService.addUserToChannelByName(channelName, userId);
    }

    @GetMapping("/")
    public String getHello(){
        return "Hello World";
    }

    @DeleteMapping("/channels/{channelId}/users/{userId}")
    public ResponseEntity<?> removeUserFromChannel(@PathVariable("channelId") String channelId, @PathVariable("userId") String userId) {
        multichannelService.removeUserFromTextchannel(userId, channelId);
        return ResponseEntity.ok("User with id " + userId + " has been removed from textchannel with id " + channelId + ".");
    }
    @DeleteMapping("/textchannels/delete-all-messages")
    public ResponseEntity<?> deleteAllMessagesFromTextchannels() {
        multichannelService.deleteAllMessagesFromTextchannels();
        userService.deleteAllMessagesFromUsers();
        messageService.deleteAllMessages();
        userService.deleteAllUsers();
        multichannelService.deleteAllTextchannels();
        return ResponseEntity.ok("All messages from every textchannel and user have been deleted.");
    }
    //Login user
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(loggedInUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @DeleteMapping("/users/email")
    public ResponseEntity<?> deleteUserByEmail(@RequestParam("email") String email) {
        User foundUser = userService.getUserByEmail(email);
        if (foundUser == null) {
            // User not found, return 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // User found, return 200 OK with the user data
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok("User with email " + email + " has been deleted.");
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable("userId") String userId) {
        User foundUser = userService.getUserById(userId);
        if (foundUser == null) {
            // User not found, return 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // User found, return 200 OK with the user data
        userService.deleteUserById(userId);
        return ResponseEntity.ok("User with id " + userId + " has been deleted.");
    }

    @DeleteMapping("/channels/name")
    public ResponseEntity<?> deleteTextchannelByName(@RequestParam("name") String name) {
        Textchannel foundTextchannel = multichannelService.getTextchannelByName(name);
        if (foundTextchannel == null) {
            // Textchannel not found, return 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        // Textchannel found, return 200 OK with the textchannel data
        multichannelService.deleteTextchannelByName(name);
        return ResponseEntity.ok("Textchannel with name " + name + " has been deleted.");
    }
}
