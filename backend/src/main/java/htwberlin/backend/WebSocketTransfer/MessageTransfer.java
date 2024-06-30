package htwberlin.backend.WebSocketTransfer;

public class MessageTransfer {
    private String id;
    private String message;
    private String date;

    private UserTransfer sender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserTransfer getSender() {
        return sender;
    }

    public void setSender(UserTransfer sender) {
        this.sender = sender;
    }
}
