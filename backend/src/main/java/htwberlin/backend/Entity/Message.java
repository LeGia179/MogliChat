package htwberlin.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
    @Id
    private String id;
    private String content;
    private String date;

    @ManyToOne
    private Textchannel textchannel;

    @ManyToOne
    private User sender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Textchannel getTextchannel() {
        return textchannel;
    }

    public void setTextchannel(Textchannel textchannel) {
        this.textchannel = textchannel;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
