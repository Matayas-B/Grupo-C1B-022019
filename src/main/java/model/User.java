package model;

public abstract class User {

    private String name;
    private String lastName;
    private String  eMail;
    private String phone;
    private String address;

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
}
