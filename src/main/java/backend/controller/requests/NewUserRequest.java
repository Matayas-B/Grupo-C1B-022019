package backend.controller.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class NewUserRequest {

    @NotEmpty(message = "Please, provide a name.")
    String name;
    @NotEmpty(message = "Please, provide a last name.")
    String lastname;
    @Email(message = "Please, provide an email.")
    String email;
    @NotEmpty(message = "Please, the password. ! ! !")
    String password;
    String phone;
    @NotEmpty(message = "Please, provide an address.")
    String address;

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public NewUserRequest() {

    }

    public NewUserRequest(String name, String lastname, String email, String password, String phone, String address) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }
}
