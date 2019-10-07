package backend.model;

import backend.model.exception.InsufficientFundsException;

import javax.persistence.*;

@Entity
public class MoneyAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MONEY_ACCOUNT_ID")
    private Long id;
    private int funds;

    public MoneyAccount() {
        funds = 0;
    }

    public Long getId() {
        return id;
    }

    public int getFunds() {
        return funds;
    }

    public boolean haveEnoughFunds(int price) {
        return funds >= price;
    }

    public void depositMoney(int money) {
        funds += money;
    }

    public void extractMoney(int money) throws InsufficientFundsException {
        if (money > funds)
            throw new InsufficientFundsException();
        funds -= money;
    }
}
