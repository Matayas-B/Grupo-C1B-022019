package model;

import model.enums.PurchaseStatus;
import org.joda.time.LocalDate;

public class Purchase {

    private int purchaseId;
    private CustomerUser customer;
    private int customerScoreId;
    private Service service;
    private Menu purchasedMenu;
    private LocalDate purchasedDate;
    private int purchaseAmount;
    private PurchaseStatus purchaseStatus;

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

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

    public void startDelivery() {
        this.purchaseStatus = PurchaseStatus.InDelivery;
    }

    public void finishDelivery() {
        this.purchaseStatus = PurchaseStatus.Finished;
    }

    public Purchase(int purchaseId, CustomerUser customer, int customerScoreId, Service service, Menu purchasedMenu, LocalDate dateOfPurchase, int purchaseAmount) {
        this.purchaseId = purchaseId;
        this.customer = customer;
        this.customerScoreId = customerScoreId;
        this.service = service;
        this.purchasedMenu = purchasedMenu;
        this.purchasedDate = dateOfPurchase;
        this.purchaseAmount = purchaseAmount;
        this.purchaseStatus = PurchaseStatus.InProgress;
    }
}
