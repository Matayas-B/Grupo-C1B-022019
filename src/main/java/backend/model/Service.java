package backend.model;

import backend.model.enums.Category;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;
import backend.model.exception.MenuNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(unique = true)
    private String serviceName;
    private String icon;
    @JoinColumn(name = "ADDRESS_ID")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String description;
    private String email;
    private String phoneNumber;
    private int deliveryDistanceKm;
    private boolean isValidService;
    @ElementCollection(targetClass = OfficeDays.class)
    @CollectionTable(name = "OFFICE_DAYS", joinColumns = @JoinColumn(name = "serviceId"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<OfficeDays> officeDays;
    @ElementCollection(targetClass = OfficeHours.class)
    @CollectionTable(name = "OFFICE_HOURS", joinColumns = @JoinColumn(name = "serviceId"))
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<OfficeHours> officeHours;

    @JoinColumn(name = "ID")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("service")
    private SupplierUser supplier;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Menu> menus = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<Menu> invalidMenus = new ArrayList<>();

    public Service() { }

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

    public void addMenu(String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        menus.add(new Menu(serviceId, name, description, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales));
    }

    public void deleteMenu(long menuId) {
        Menu menuToDelete = menus.stream().filter(m -> m.getMenuId() == menuId).findFirst().orElseThrow(MenuNotFoundException::new);
        menus.remove(menuToDelete);
    }

    public SupplierUser getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierUser supplier) {
        this.supplier = supplier;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public int getDeliveryDistanceKm() {
        return deliveryDistanceKm;
    }

    public boolean isValidService() {
        return isValidService;
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

    public List<Menu> getValidMenus() {
        return menus;
    }

    public List<Menu> getInvalidMenus() { return invalidMenus; }

    public Menu getMenuByMenuId(long menuId) {
        return menus.stream().filter(m -> m.getMenuId() == menuId).findFirst().orElseThrow(MenuNotFoundException::new);
    }

    public void markMenuAsInvalid(Menu invalidMenu) {
        invalidMenu.markAsInvalid();
        getValidMenus().remove(invalidMenu);
        getInvalidMenus().add(invalidMenu);
    }

    public void markServiceAsInvalid() {
        isValidService = false;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }
}
