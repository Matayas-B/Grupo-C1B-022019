package model;

public class Address {
    private String town;
    private String location;

    public String getTown() {
        return town;
    }

    public Address(String town, String location) {
        this.town = town;
        this.location = location;
    }
}
