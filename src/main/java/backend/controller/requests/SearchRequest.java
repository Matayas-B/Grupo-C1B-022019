package backend.controller.requests;

import backend.model.enums.Category;

public class SearchRequest {
    String menuname;
    Category menucategory;
    String servicetown;

    public String getMenuName() {
        return menuname;
    }

    public Category getMenuCategory() {
            return menucategory;
    }

    public String getServiceTown() {
        return servicetown;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }

    public void setMenucategory(Category menucategory) {
        this.menucategory = menucategory;
    }

    public void setServicetown(String servicetown) {
        this.servicetown = servicetown;
    }

    public String toString() {
        return String.format("Menu name: %s, Menu category: %s, Service town: %s.", menuname, menucategory, servicetown);
    }
}
