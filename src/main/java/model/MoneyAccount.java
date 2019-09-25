package model;

public class MoneyAccount {

    private int funds;

    public MoneyAccount() {
        funds = 0;
    }

    public boolean haveEnoughFunds(int price) {
        return funds >= price;
    }

    public void depositMoney(int money) {
        funds = funds + money;
    }

    public void extractMoney(int money) throws Exception {
        if (money > funds)
            throw new Exception("You do not have enough money on your account.");

        funds -= money;
    }
}
