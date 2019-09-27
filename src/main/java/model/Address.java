package model;

public class Address {

    private String town;
    private String location;

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public Address(String town, String location) {
        this.town = town;
        this.location = location;
    }
}
