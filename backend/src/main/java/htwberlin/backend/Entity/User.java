package htwberlin.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User {
    @Id
    private String id;

    @Column(nullable = false)
    @NotNull
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String password;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Message> messages;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Textchannel> textchannels;

    @ManyToMany()
    private List<Directchannel> directchannels;

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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Textchannel> getTextchannels() {
        return textchannels;
    }

    public void setTextchannels(List<Textchannel> textchannels) {
        this.textchannels = textchannels;
    }

    public List<Directchannel> getDirectchannels() {
        return directchannels;
    }

    public void setDirectchannels(List<Directchannel> directchannels) {
        this.directchannels = directchannels;
    }
}
