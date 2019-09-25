package exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException() {
        super("Menu does not longer exists.");
    }
}
