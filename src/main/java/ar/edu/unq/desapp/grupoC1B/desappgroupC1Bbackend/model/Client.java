package ar.edu.unq.desapp.grupoC1B.desappgroupC1Bbackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String lastname;
    private String eMail;
    private String phone;
    private String Locate;
    private String adrres;

    public Client(Long id, String name, String lastname, String eMail, String phone, String locate, String adrres) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.eMail = eMail;
        this.phone = phone;
        Locate = locate;
        this.adrres = adrres;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String geteMail() {
        return eMail;
    }

    public String getPhone() {
        return phone;
    }

    public String getLocate() {
        return Locate;
    }

    public String getAdrres() {
        return adrres;
    }
}
