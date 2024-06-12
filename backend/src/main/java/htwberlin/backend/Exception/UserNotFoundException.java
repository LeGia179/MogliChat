package htwberlin.backend.Exception;

public class UserNotFoundException extends RuntimeException {
    //Konstruktor der Fehlernachricht entgegennimmt
    public UserNotFoundException(String message) {
        super(message);
    }
}