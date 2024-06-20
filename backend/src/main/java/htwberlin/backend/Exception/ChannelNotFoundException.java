package htwberlin.backend.Exception;

public class ChannelNotFoundException extends RuntimeException {
    //Konstruktor der Fehlernachricht entgegennimmt
    public ChannelNotFoundException(String message) {
        super(message);
    }
}