package backend.model;

import javax.persistence.*;

@Entity
public class CustomerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_SCORE_ID")
    private Long customerScoreId;

    private String customerEmail;
    private long serviceId;
    private long menuId;
    private int punctuation;
    private boolean isFinished;

    public Long getCustomerScoreId() {
        return customerScoreId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setCustomerScoreId(Long customerScoreId) {
        this.customerScoreId = customerScoreId;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public CustomerScore() {}

    public CustomerScore(String customerEmail, long serviceId, long menuId) {
        this.customerEmail = customerEmail;
        this.serviceId = serviceId;
        this.menuId = menuId;
        this.punctuation = 0;
        this.isFinished = false;
    }

    void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
        this.isFinished = true;
    }
}