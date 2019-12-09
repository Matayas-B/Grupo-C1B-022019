package backend.model.exception;

public class ExistingServiceException extends RuntimeException {
    public ExistingServiceException() {
        super("Supplier already has a service. Please, delete it before creating new one");
    }

    public ExistingServiceException(String serviceName) {
        super("There is already a service with the same name: " + serviceName);
    }
}
