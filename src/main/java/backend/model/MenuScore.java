package backend.model;

public class MenuScore {

    private String customerName;
    private int punctuation;

    public int getPunctuation() {
        return punctuation;
    }

    public MenuScore(String customerName, int punctuation) {
        this.customerName = customerName;
        this.punctuation = punctuation;
    }
}
