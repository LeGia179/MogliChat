package htwberlin.backend.WebSocketTransfer;

import lombok.Setter;
import lombok.Getter;


public class ChatTransfer {
    private String content;
    private String date;
    private String sender;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}

