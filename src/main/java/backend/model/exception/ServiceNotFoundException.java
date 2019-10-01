package backend.model.exception;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException() {
        super("Service does not longer exists.");
    }
}
