package model;

import org.joda.time.LocalDate;

public class Purchase {

    private CustomerUser customer;
    private Service service;
    private Menu purchasedMenu;
    private LocalDate purchasedDate;

    public CustomerUser getCustomer() {
        return customer;
    }

    public Service getService() {
        return service;
    }

    public Menu getPurchasedMenu() {
        return purchasedMenu;
    }

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public Purchase(CustomerUser customer, Service service, Menu purchasedMenu, LocalDate dateOfPurchase) {
        this.customer = customer;
        this.service = service;
        this.purchasedMenu = purchasedMenu;
        this.purchasedDate = dateOfPurchase;
    }
}
