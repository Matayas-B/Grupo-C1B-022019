package backend.model;

import backend.controller.helpers.LocalDateSerializer;
import backend.model.enums.Category;
import backend.model.enums.OfficeHours;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MENU_ID")
    private int menuId;

    private long serviceId;
    @Column(unique = true)
    private String name;
    private String description;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Category category;
    private int deliveryFee;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private OfficeHours deliveryHours;
    private int averageDeliveryMinutes;
    private int price;
    private int minQuantity;
    private int minQuantityPrice;
    private int maxDailySales;
    private boolean isValidMenu;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MenuScore> menuScores = new ArrayList<>();

    public Menu() {
    }

    public Menu(long serviceId, String name, String description, String imageUrl, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.deliveryFee = deliveryFee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deliveryHours = deliveryHours;
        this.averageDeliveryMinutes = averageDeliveryMinutes;
        this.price = price;
        this.minQuantity = minQuantity;
        this.minQuantityPrice = minQuantityPrice;
        this.maxDailySales = maxDailySales;
        this.isValidMenu = true;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public void setMenuScores(List<MenuScore> menuScores) {
        this.menuScores = menuScores;
    }

    public List<MenuScore> getMenuScores() {
        return menuScores;
    }

    MenuScore addScore(String customerEmail, int punctuation) {
        MenuScore menuScore = new MenuScore(customerEmail, punctuation);
        menuScores.add(menuScore);
        return menuScore;
    }

    boolean hasEnoughScores() {
        return menuScores.size() >= 20;
    }

    public Double getScoreAverage() {
        OptionalDouble averageScore = menuScores.stream().mapToDouble(MenuScore::getPunctuation).average();
        return averageScore.isPresent() ? averageScore.getAsDouble() : 0;
    }

    void markAsInvalid() {
        this.isValidMenu = false;
    }

    public boolean isValidMenu() {
        return isValidMenu;
    }

    public void setValidMenu(boolean validMenu) {
        isValidMenu = validMenu;
    }

    public int getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public OfficeHours getDeliveryHours() {
        return deliveryHours;
    }

    public void setDeliveryHours(OfficeHours deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

    public int getAverageDeliveryMinutes() {
        return averageDeliveryMinutes;
    }

    public void setAverageDeliveryMinutes(int averageDeliveryMinutes) {
        this.averageDeliveryMinutes = averageDeliveryMinutes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getMinQuantityPrice() {
        return minQuantityPrice;
    }

    public void setMinQuantityPrice(int minQuantityPrice) {
        this.minQuantityPrice = minQuantityPrice;
    }

    public int getMaxDailySales() {
        return maxDailySales;
    }

    public void setMaxDailySales(int maxDailySales) {
        this.maxDailySales = maxDailySales;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
