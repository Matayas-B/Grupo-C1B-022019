package model;

import model.enums.PurchaseStatus;
import org.joda.time.LocalDate;

public class HistoricalPurchases {

    private LocalDate purchasedDate;
    private PurchaseStatus purchaseStatus;
    private int punctuation;
    private Menu purchasedMenu;
    private int purchaseAmount;

    public HistoricalPurchases(LocalDate purchasedDate, PurchaseStatus purchaseStatus, int punctuation, Menu purchasedMenu, int purchaseAmount) {
        this.purchasedDate = purchasedDate;
        this.purchaseStatus = purchaseStatus;
        this.punctuation = punctuation;
        this.purchasedMenu = purchasedMenu;
        this.purchaseAmount = purchaseAmount;
    }
}
