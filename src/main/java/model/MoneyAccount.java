package model;

import exception.CurrencyMenuException;

public class MoneyAccount {

    private int funds;

    public MoneyAccount(){
        funds = 0;
    }

    public int getFunds(){
        return funds;
    }

    public void depositMoney(int money) {
        funds = funds + money;
    }

    public void extractMoney(int money) throws Exception {
        if(money > funds)
            throw new Exception("No tiene suficiente Dinero");

        funds -= money;
    }
}
