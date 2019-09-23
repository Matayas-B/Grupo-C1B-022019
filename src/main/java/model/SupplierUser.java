package model;

import org.joda.time.LocalDate;

import exception.CurrencyMenuException;

import java.util.ArrayList;
import java.util.List;

public class SupplierUser extends User {


    private ArrayList<Menu> menus = new ArrayList<Menu>();
    private MoneyAccount account = new MoneyAccount();

    public SupplierUser(String name, String lastName, String eMail, String phone, String address) {
        super(name, lastName, eMail, phone, address);
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }



    public void addMenu(Menu menu){

        if(getCantCurrentMenues() >= 20){
            throw new CurrencyMenuException("Superaste el maximo de menues vigentes") ;
        }else {
            menus.add(menu);
        }
    }

    private int getCantCurrentMenues() {

            int count = 0;
            for( Menu i : this.menus){

                if(i.getValidateEnd().isAfter(LocalDate.now())){
                    count++;
                }
            }
            return count;
    }
}
