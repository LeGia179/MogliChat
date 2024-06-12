package htwberlin.backend.Exception;

public class IncorrectPasswordException extends RuntimeException {
    //Konstruktor der Fehlernachricht entgegennimmt
    public IncorrectPasswordException(String message) {
        super(message);
    }
}
