package backend.controller.requests;

import backend.model.enums.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SignUpRequest {

    @NotEmpty(message = "Please, provide a name.")
    private String name;
    @NotEmpty(message = "Please, provide a last name.")
    String lastname;
    String phone;
    @NotEmpty(message = "Please, provide an address.")
    String address;

    @NotEmpty(message = "Please, provide an email.")
    @Email
    private String email;
    @NotEmpty(message = "Please, provide a password.")
    private String password;
    @NotNull
    private UserType usertype;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }
}