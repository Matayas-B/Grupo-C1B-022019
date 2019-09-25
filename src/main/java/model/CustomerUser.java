package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity

public class CustomerUser extends User{

    private int id;

    public MoneyAccount getAccount() {
        return account;
    }

    private MoneyAccount account = new MoneyAccount();

    public CustomerUser(String name, String lastName, String eMail, String phone, String address) {
        super(name, lastName, eMail, phone, address);
    }
}
