package backend.model.exception;

public class InvalidServiceException extends RuntimeException {
    public InvalidServiceException() {
        super("This service is not valid anymore.");
    }
}
