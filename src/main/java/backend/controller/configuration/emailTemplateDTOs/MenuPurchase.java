package backend.controller.configuration.emailTemplateDTOs;

public class MenuPurchase {
    private String menuName;
    private int quantity;
    private int purchaseAmount;

    public String getMenuName() {
        return menuName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public MenuPurchase(String menuName, int quantity, int purchaseAmount) {
        this.menuName = menuName;
        this.quantity = quantity;
        this.purchaseAmount = purchaseAmount;
    }
}
