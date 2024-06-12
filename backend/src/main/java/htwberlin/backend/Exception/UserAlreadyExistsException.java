package htwberlin.backend.Exception;

public class UserAlreadyExistsException extends RuntimeException {
    //Konstruktor der Fehlernachricht entgegennimmt
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
