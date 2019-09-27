package model;

public class CustomerScore {

    private String customerName;
    private Service service;
    private Menu menu;
    private int punctuation;
    private boolean isFinished;

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

    CustomerScore(String customerName, Service service, Menu menu) {
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