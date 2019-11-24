package backend.controller.configuration.emailTemplateDTOs;

import backend.controller.helpers.LocalDateSerializer;
import backend.model.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDate;

import java.util.List;

public class UserPurchases {

    private User user;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate today;
    private int totalAmount;
    private List<MenuPurchase> menuPurchases;

    public User getUser() {
        return user;
    }

    public LocalDate getToday() {
        return today;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public List<MenuPurchase> getMenuPurchases() {
        return menuPurchases;
    }

    public UserPurchases(User user, LocalDate today, int totalAmount, List<MenuPurchase> menuPurchases) {
        this.user = user;
        this.today = today;
        this.totalAmount = totalAmount;
        this.menuPurchases = menuPurchases;
    }
}
