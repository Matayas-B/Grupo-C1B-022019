package model;

public class Purchase {

    private CustomerUser customer;
    private Menu purchasedMenu;
    // TODO: Add List<Score>

    public Purchase(CustomerUser customer, Menu purchasedMenu) {
        this.customer = customer;
        this.purchasedMenu = purchasedMenu;
    }
}
