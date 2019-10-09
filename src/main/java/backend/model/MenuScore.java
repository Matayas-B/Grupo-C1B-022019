package backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

@Entity
public class MenuScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_SCORE_ID")
    private long menuScoreId;
    private String customerName;
    private int punctuation;

    public void setMenuScoreId(long menuScoreId) {
        this.menuScoreId = menuScoreId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    public long getMenuScoreId() {
        return menuScoreId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public MenuScore() {}

    public MenuScore(String customerName, int punctuation) {
        this.customerName = customerName;
        this.punctuation = punctuation;
    }
}
