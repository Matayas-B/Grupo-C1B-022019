package backend.model.exception;

public class ExistingServiceException extends RuntimeException {
    public ExistingServiceException() {
        super("Supplier already has a service. Please, delete it before creating new one");
    }
}
