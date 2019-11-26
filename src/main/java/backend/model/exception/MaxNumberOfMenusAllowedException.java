package backend.model.exception;

public class MaxNumberOfMenusAllowedException extends RuntimeException {
    public MaxNumberOfMenusAllowedException(long id) {
        super("Service with id " + id + " has reached the maximum number of allowed menus.");
    }
}
