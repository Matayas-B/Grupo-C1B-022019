package backend.model;

import backend.model.builders.MenuBuilder;
import backend.model.builders.ServiceBuilder;
import backend.model.enums.Category;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SearchTest {

    @Test
    public void SearchByMenuNameShouldBringAllMenusWithThatName() throws Exception {
        // Arrange
        Service service1 = new ServiceBuilder(1, "Burguer King", null).build();
        Menu menu1 = new MenuBuilder(1).setMenuName("Whopper").setCategory(Category.Hamburguesa).build();
        Menu menu2 = new MenuBuilder(3).setMenuName("BK Stacker").setCategory(Category.Hamburguesa).build();
        Menu menu3 = new MenuBuilder(2).setMenuName("BK Salad").setCategory(Category.Green).build();
        service1.getValidMenus().add(menu1);
        service1.getValidMenus().add(menu2);
        service1.getValidMenus().add(menu3);

        List<Service> services = new ArrayList<>();
        services.add(service1);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);

        // Act
        Search search = new Search();
        List<Menu> menusFilteredByName = search.search(services, menus,"BK", Category.All, "");

        // Assert
        assertFalse(menusFilteredByName.isEmpty());
        assertEquals(menusFilteredByName.size(), 2);
    }

    @Test
    public void SearchByCategoryShouldBringAllMenusWithThatCategory() throws Exception {
        // Arrange
        Service service1 = new ServiceBuilder(1,"Burguer King", null).build();
        Menu menu1 = new MenuBuilder(1).setMenuName("Whopper").setCategory(Category.Hamburguesa).build();
        Menu menu2 = new MenuBuilder(3).setMenuName("BK Stacker").setCategory(Category.Hamburguesa).build();
        Menu menu3 = new MenuBuilder(2).setMenuName("BK Salad").setCategory(Category.Green).build();
        service1.getValidMenus().add(menu1);
        service1.getValidMenus().add(menu2);
        service1.getValidMenus().add(menu3);

        List<Service> services = new ArrayList<>();
        services.add(service1);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);

        // Act
        Search search = new Search();
        List<Menu> menusFilteredByCategory = search.search(services, menus,"", Category.Hamburguesa, "");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 2);
    }

    @Test
    public void SearchByTownShouldBringAllMenusWhichServiceBelongsToThatTown() throws Exception {
        // Arrange
        Service service1 = new ServiceBuilder(1,"Burguer King", null).build();
        Service service2 = new ServiceBuilder(1,"McDonalds", null).setAddressTown("Bernal").build();
        Menu menu1 = new MenuBuilder(1).setMenuName("Whopper").setCategory(Category.Hamburguesa).build();
        Menu menu2 = new MenuBuilder(3).setMenuName("BK Stacker").setCategory(Category.Hamburguesa).build();
        Menu menu3 = new MenuBuilder(2).setMenuName("Mc Pollo").setCategory(Category.Hamburguesa).build();
        service1.getValidMenus().add(menu1);
        service1.getValidMenus().add(menu2);
        service2.getValidMenus().add(menu3);

        List<Service> services = new ArrayList<>();
        services.add(service1);
        services.add(service2);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);

        // Act
        Search search = new Search();
        List<Menu> menusFilteredByCategory = search.search(services, menus,"", Category.All, "Bernal");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 1);
    }

    @Test
    public void SearchWithAllParametersShouldBringMenusAccordingToCriteria() throws Exception {
        // Arrange
        Service service1 = new ServiceBuilder(1,"Burguer King", null).setAddressTown("Bernal").build();
        Service service2 = new ServiceBuilder(1,"McDonalds", null).setAddressTown("Bernal").build();
        Service service3 = new ServiceBuilder(1,"Mostaza", null).setAddressTown("Quilmes").build();
        Menu menu1 = new MenuBuilder(1).setMenuName("Whopper").setCategory(Category.Hamburguesa).build();
        Menu menu2 = new MenuBuilder(2).setMenuName("Mc Pollo").setCategory(Category.Hamburguesa).build();
        Menu menu3 = new MenuBuilder(3).setMenuName("Mostaza Whopper").setCategory(Category.Hamburguesa).build();
        service1.getValidMenus().add(menu1);
        service2.getValidMenus().add(menu2);
        service3.getValidMenus().add(menu3);

        List<Service> services = new ArrayList<>();
        services.add(service1);
        services.add(service2);
        services.add(service3);
        List<Menu> menus = new ArrayList<>();
        menus.add(menu1);
        menus.add(menu2);
        menus.add(menu3);

        // Act
        Search search = new Search();
        List<Menu> menusFilteredByCategory = search.search(services, menus,"Whopper", Category.Hamburguesa, "Bernal");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 1);
        assertEquals(menusFilteredByCategory.get(0).getName(), "Whopper");
        assertEquals(menusFilteredByCategory.get(0).getMenuId(), 1);
    }
}
