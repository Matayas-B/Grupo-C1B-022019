package backend.controller.requests;

import backend.model.enums.Category;
import backend.model.enums.OfficeHours;
import org.joda.time.LocalDate;

import javax.validation.constraints.*;

public class MenuRequest {
    @Positive
    long serviceId;
    @PositiveOrZero
    long menuId;
    @NotEmpty(message = "Please, provide a name to the menu.")
    String name;
    @NotEmpty(message = "Describe what your menu is about ! ! !")
    String description;
    String imageurl;
    @NotNull(message = "Select a Category for the service.")
    Category category;
    @NotNull(message = "How much does the delivery cost?")
    int deliveryFee;
    @NotNull
    LocalDate startDate;
    @NotNull
    LocalDate endDate;
    @NotNull(message = "Select the delivery hours.")
    OfficeHours deliveryHours;
    @Min(5)
    int averageDeliveryMinutes;
    @NotNull
    int price;
    @NotNull
    int minQuantity;
    @NotNull
    int minQuantityPrice;
    @NotNull
    int maxDailySales;

    public long getServiceId() {
        return serviceId;
    }

    public long getMenuId() {
        return menuId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public int getDeliveryFee() {
        return deliveryFee;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public OfficeHours getDeliveryHours() {
        return deliveryHours;
    }

    public int getAverageDeliveryMinutes() {
        return averageDeliveryMinutes;
    }

    public int getPrice() {
        return price;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public int getMinQuantityPrice() {
        return minQuantityPrice;
    }

    public int getMaxDailySales() {
        return maxDailySales;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDeliveryFee(int deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDeliveryHours(OfficeHours deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

    public void setAverageDeliveryMinutes(int averageDeliveryMinutes) {
        this.averageDeliveryMinutes = averageDeliveryMinutes;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setMinQuantityPrice(int minQuantityPrice) {
        this.minQuantityPrice = minQuantityPrice;
    }

    public void setMaxDailySales(int maxDailySales) {
        this.maxDailySales = maxDailySales;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
