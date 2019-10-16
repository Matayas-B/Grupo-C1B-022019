package backend.model;

import backend.model.enums.PurchaseStatus;
import org.joda.time.LocalDate;

public class HistoricalPurchases {

    private LocalDate purchasedDate;
    private long purchaseId;
    private PurchaseStatus purchaseStatus;
    private int punctuation;
    private Menu purchasedMenu;
    private int purchaseAmount;

    public LocalDate getPurchasedDate() {
        return purchasedDate;
    }

    public PurchaseStatus getPurchaseStatus() {
        return purchaseStatus;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public Menu getPurchasedMenu() {
        return purchasedMenu;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchasedDate(LocalDate purchasedDate) {
        this.purchasedDate = purchasedDate;
    }

    public void setPurchaseStatus(PurchaseStatus purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public void setPunctuation(int punctuation) {
        this.punctuation = punctuation;
    }

    public void setPurchasedMenu(Menu purchasedMenu) {
        this.purchasedMenu = purchasedMenu;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    HistoricalPurchases(LocalDate purchasedDate, long purchaseId, PurchaseStatus purchaseStatus, int punctuation, Menu purchasedMenu, int purchaseAmount) {
        this.purchasedDate = purchasedDate;
        this.purchaseId = purchaseId;
        this.purchaseStatus = purchaseStatus;
        this.punctuation = punctuation;
        this.purchasedMenu = purchasedMenu;
        this.purchaseAmount = purchaseAmount;
    }
}
