package model;

import exception.CurrencyMenuException;



public class MoneyAccount {

    private int account;

    public MoneyAccount(){
        account = 0;
    }


    public int getAccount(){
        return  account;
    }

    public void addMoney(int diner){
        account = account + diner;
    }

    public void extract(int diner){

            if(diner >= account){
                throw new CurrencyMenuException("No tiene suficiente Dinero") ;
            }

            account = account - diner;
    }
}
