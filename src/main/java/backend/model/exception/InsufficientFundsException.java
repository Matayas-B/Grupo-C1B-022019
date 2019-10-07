package backend.model.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("You do not have enough money on your account.");
    }
}
