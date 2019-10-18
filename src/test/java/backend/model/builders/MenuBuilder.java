package backend.model.builders;

import backend.model.Menu;
import backend.model.MenuScore;
import backend.model.enums.Category;
import backend.model.enums.OfficeHours;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

public class MenuBuilder {

    private int menuId;
    private String name = "Menu Test";
    private String description = "Menu Test description";
    private Category category = Category.Hamburguesa;
    private int deliveryFee = 10;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();
    private OfficeHours deliveryHours = OfficeHours.Afternoon;
    private int averageDeliveryMinutes = 15;
    private int price = 50;
    private int minQuantity = 10;
    private int minQuantityPrice = 50;
    private int maxDailySales = 1000;
    private boolean isValidMenu = true;
    List<MenuScore> menuScores = new ArrayList<>();

    public MenuBuilder(int menuId) {
        this.menuId = menuId;
    }

    public MenuBuilder setMenuName(String menuName) {
        this.name = menuName;
        return this;
    }

    public MenuBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public MenuBuilder setMaxDailySales(int maxSales) {
        this.maxDailySales = maxSales;
        return this;
    }

    public Menu build() {
        Menu menu = new Menu();
        menu.setMenuId(this.menuId);
        menu.setName(this.name);
        menu.setDescription(this.description);
        menu.setCategory(this.category);
        menu.setDeliveryFee(this.deliveryFee);
        menu.setStartDate( this.startDate);
        menu.setEndDate(this.endDate);
        menu.setDeliveryHours(this.deliveryHours);
        menu.setAverageDeliveryMinutes(this.averageDeliveryMinutes);
        menu.setPrice(this.price);
        menu.setMinQuantity(this.minQuantity);
        menu.setMinQuantityPrice(this.minQuantityPrice);
        menu.setMaxDailySales(this.maxDailySales);
        menu.setValidMenu(this.isValidMenu);
        return menu;
    }
}
