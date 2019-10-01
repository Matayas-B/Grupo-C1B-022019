package backend.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String eMail;
    private String phone;
    private String address;

    public User(){}

    public User(String name, String lastName, String eMail, String phone, String address) {
        this.name = name;
        this.lastName = lastName;
        this.eMail = eMail;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void seteMail(String eMail) {this.eMail = eMail;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setAddress(String address) {this.address = address;}
}
