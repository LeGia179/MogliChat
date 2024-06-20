package htwberlin.backend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity//speichert die Nachrichten-Daten in einer DB als JPA-Entity
public class ChatMessageEntity {
    @Id//marked id als Primärschlüssel
    private String id;
    private String userName;
    private String message;
    private LocalDateTime timestamp;
    @ManyToOne()
    private ChatChannelEntity channel;

    @ManyToOne
    private UserEntity user;



    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public ChatChannelEntity getChannel() {
        return channel;
    }

    public void setChannel(ChatChannelEntity channel) {
        this.channel = channel;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}