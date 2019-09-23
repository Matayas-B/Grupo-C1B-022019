package model;

import exception.CurrencyMenuException;

import java.security.PublicKey;

public class MoneyAccount {

    private double account;

    public MoneyAccount(){
        account = 0;
    }


    public double getAccount(){
        return  account;
    }

    public void addMoney(Double diner){
        account = account + diner;
    }

    public void extract(double diner){

            if(diner >= account){
                throw new CurrencyMenuException("No tiene suficiente Dinero") ;
            }

            account = account - diner;
    }
}
