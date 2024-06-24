package htwberlin.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
@Entity//speichert die Nachrichten-Daten in einer DB als JPA-Entity
public class ChatMessageEntity {
    @Id//marked id als Primärschlüssel
    private String id;
    private String userName;
    private String message;
    private LocalDateTime timestamp;

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
}