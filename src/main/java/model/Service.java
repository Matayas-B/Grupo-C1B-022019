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
    private Address address;
    private String description;
    private String email;
    private String phoneNumber;
    private List<OfficeDays> officeDays;
    private List<OfficeHours> officeHours;
    private int deliveryDistanceKm;
    private SupplierUser supplier;
    private boolean isValidService;

    private List<Menu> menus = new ArrayList<>();
    private List<Menu> invalidMenus = new ArrayList<>();

    /* Constructor for testing purposes -> ServiceBuilder */
    public Service(String serviceName, SupplierUser supplier) {
        this.serviceName = serviceName;
        this.supplier = supplier;
    }

    public Service(String serviceName, String icon, Address address, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance, SupplierUser supp) {
        this.serviceName = serviceName;
        this.icon = icon;
        this.address = address;
        this.description = description;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.officeDays = officeDays;
        this.officeHours = officeHours;
        this.deliveryDistanceKm = deliveryDistance;
        this.supplier = supp;
        this.isValidService = true;
    }

    public void addMenu(int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        menus.add(new Menu(id, name, description, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales));
    }

    public SupplierUser getSupplier() {
        return supplier;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public List<Menu> getInvalidMenus() { return invalidMenus; }

    public Menu getMenuByMenuId(int menuId) {
        return menus.stream().filter(m -> m.getMenuId() == menuId).findFirst().orElse(null);
    }

    public void markMenuAsInvalid(Menu invalidMenu) {
        invalidMenu.markAsInvalid();
        getMenus().remove(invalidMenu);
        getInvalidMenus().add(invalidMenu);
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
