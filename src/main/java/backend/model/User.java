package backend.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "user")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String address;
    @Column(name = "USER_TYPE", insertable = false, updatable = false)
    private String userType;

    @JoinColumn(name="MONEY_ACCOUNT_ID", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private MoneyAccount account;

    public User() { }

    public User(String name, String lastName, String email, String password, String phone, String address) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.account = new MoneyAccount();
    }

    public MoneyAccount getAccount() {
        return account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAccount(MoneyAccount account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {this.lastName = lastName;}

    public void setEmail(String email) {this.email = email;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setAddress(String address) {this.address = address;}
}
