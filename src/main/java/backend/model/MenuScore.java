package backend.model;

import javax.persistence.*;

@Entity
public class MenuScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SCORE_ID")
    private long menuScoreId;
    private String customerEmail;
    private int punctuation;

    public void setMenuScoreId(long menuScoreId) {
        this.menuScoreId = menuScoreId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    public long getMenuScoreId() {
        return menuScoreId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public MenuScore() {}

    public MenuScore(String customerEmail, int punctuation) {
        this.customerEmail = customerEmail;
        this.punctuation = punctuation;
    }
}
