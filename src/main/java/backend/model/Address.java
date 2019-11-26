package backend.model;

import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESS_ID")
    private Long addressId;
    private String town;
    private String location;

    public String getTown() {
        return town;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getLocation() {
        return location;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Address() { }

    public Address(String town, String location) {
        this.town = town;
        this.location = location;
    }
}
