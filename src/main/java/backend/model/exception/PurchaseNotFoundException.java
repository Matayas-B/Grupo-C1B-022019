package backend.model.exception;

public class PurchaseNotFoundException extends RuntimeException {
    public PurchaseNotFoundException() {
        super("Purchase was not made.");
    }
}
