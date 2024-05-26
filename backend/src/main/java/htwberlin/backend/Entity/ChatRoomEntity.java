package htwberlin.backend.Entity;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomEntity {
    private String name;
    private List<ChatMessageEntity> messages;

    public ChatRoomEntity(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<ChatMessageEntity> getMessages() {
        return messages;
    }
    public void setMessages(List<ChatMessageEntity> messages) {
        this.messages = messages;
    }
    public void addMessage(ChatMessageEntity message) {
        this.messages.add(message);
    }
}
