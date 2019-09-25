package model;

import model.enums.Category;
import model.enums.OfficeHours;
import org.joda.time.LocalDate;


public class Menu {

    private int menuId;
    private String name;
    private String description;
    private Category category;
    private int deliveryFee;
    private LocalDate startDate;
    private LocalDate endDate;
    private OfficeHours deliveryHours;
    private int averageDeliveryMinutes;
    private int price;
    private int minQuantity;
    private int minQuantityPrice;
    private int maxDailySales;

    public Menu(int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        this.menuId = id;
        this.name = name;
        this.description = description;
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
}
