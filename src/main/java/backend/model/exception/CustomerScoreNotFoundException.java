package backend.model.exception;

public class CustomerScoreNotFoundException extends RuntimeException {
    public CustomerScoreNotFoundException() {
        super("User Score does not exists.");
    }
}
