package model;

import model.enums.Category;
import model.enums.OfficeDays;
import model.enums.OfficeHours;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private int serviceId;
    private String serviceName;
    private String icon;
    private String address;
    private String description;
    private String email;
    private String phoneNumber;
    private List<OfficeDays> officeDays;
    private List<OfficeHours> officeHours;
    private int deliveryDistanceKm;

    private List<Menu> menus = new ArrayList<Menu>();

    public Service(String serviceName, String icon, String address, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance) {
        this.serviceName = serviceName;
        this.icon = icon;
        this.address = address;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.officeDays = officeDays;
        this.officeHours = officeHours;
        this.deliveryDistanceKm = deliveryDistance;
    }

    public void addMenu(int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales){
        menus.add(new Menu(id, name, description, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales));
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<OfficeDays> getOfficeDays() {
        return officeDays;
    }

    public void setOfficeDays(List<OfficeDays> officeDays) {
        this.officeDays = officeDays;
    }

    public List<OfficeHours> getOfficeHours() {
        return officeHours;
    }

    public void setOfficeHours(List<OfficeHours> officeHours) {
        this.officeHours = officeHours;
    }

    public int getDeliveryDistance() {
        return deliveryDistanceKm;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistanceKm = deliveryDistance;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
