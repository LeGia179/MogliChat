package htwberlin.backend.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity//speichert die User-Daten in einer DB als JPA-Entity
public class UserEntity {
    @Id//marked id als Primärschlüssel
    private String id;
    private String username;
    private String email;
    private String password;

    @ManyToMany
    private List<ChatChannelEntity> channel;

    @OneToMany
    private List<ChatMessageEntity> message;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ChatChannelEntity> getChannel() {
        return channel;
    }

    public void setChannel(List<ChatChannelEntity> channel) {
        this.channel = channel;
    }
}