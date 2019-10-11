package backend.model;

import javax.persistence.*;

@Entity
public class CustomerScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_SCORE_ID")
    private int customerScoreId;
    private String customerName;

    @Transient
    //@JoinColumn(name = "SERVICE_ID")
    //@OneToOne(cascade = CascadeType.ALL)
    private Service service; // Missing
    @Transient
    private Menu menu; // Missing

    private int punctuation;
    private boolean isFinished;

    public int getCustomerScoreId() {
        return customerScoreId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Service getService() {
        return service;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getPunctuation() {
        return punctuation;
    }

    boolean isFinished() {
        return isFinished;
    }

    public CustomerScore() {}

    public CustomerScore(int customerScoreId, String customerName, Service service, Menu menu) {
        this.customerScoreId = customerScoreId;
        this.customerName = customerName;
        this.service = service;
        this.menu = menu;
        this.punctuation = 0;
        this.isFinished = false;
    }

    void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
        this.isFinished = true;
    }
}