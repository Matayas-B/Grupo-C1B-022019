package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class CustomerUser extends User{


    public CustomerUser(String name, String lastName, String eMail, String phone, String address, Long id) {
        super(name, lastName, eMail, phone, address);

    }

}
