package backend.model;

import backend.model.builders.MenuBuilder;
import backend.model.builders.ServiceBuilder;
import backend.model.enums.Category;
import org.junit.Test;
import backend.repository.UnityOfWork;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SearchTest {

    @Test
    public void SearchByMenuNameShouldBringAllMenusWithThatName() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        UnityOfWork unityOfWork = new UnityOfWork();
        ViendasYaFacade viendasYa = new ViendasYaFacade(unityOfWork);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, new ServiceBuilder("Burguer King", supplier).build());

        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(2).setMenuName("Whopper XL").build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(3).setMenuName("BK Stacker").build());

        // Act
        List<Menu> menusFilteredByName = Search.search(unityOfWork,"Whopper", null, "");

        // Assert
        assertFalse(menusFilteredByName.isEmpty());
        assertEquals(menusFilteredByName.size(), 2);
    }

    @Test
    public void SearchByCategoryShouldBringAllMenusWithThatCategory() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        UnityOfWork unityOfWork = new UnityOfWork();
        ViendasYaFacade viendasYa = new ViendasYaFacade(unityOfWork);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, new ServiceBuilder("Burguer King", supplier).build());

        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setCategory(Category.Hamburguesa).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(2).setCategory(Category.Hamburguesa).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(3).setCategory(Category.Green).build());

        // Act
        List<Menu> menusFilteredByCategory = Search.search(unityOfWork,"", Category.Hamburguesa, "");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 2);
    }

    @Test
    public void SearchByTownShouldBringAllMenusWhichServiceBelongsToThatTown() throws Exception {
        // Arrange
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");

        UnityOfWork unityOfWork = new UnityOfWork();
        ViendasYaFacade viendasYa = new ViendasYaFacade(unityOfWork);
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);

        viendasYa.addService(supplier1, new ServiceBuilder("Burguer King", supplier1).setAddressTown("Bernal").build());
        viendasYa.addService(supplier2, new ServiceBuilder("McDonalds", supplier2).setAddressTown("Bernal").build());

        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(2).build());
        viendasYa.addMenuToService("McDonalds", new MenuBuilder(3).build());

        // Act
        List<Menu> menusFilteredByCategory = Search.search(unityOfWork,"", null, "Bernal");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 3);
    }

    @Test
    public void SearchWithAllParametersShouldBringMenusAccordingToCriteria() throws Exception {
        // Arrange
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier3 = new SupplierUser("Brian", "Loquillo", "bra@gmail.com", "1161635613", "Canale 3134");

        UnityOfWork unityOfWork = new UnityOfWork();
        ViendasYaFacade viendasYa = new ViendasYaFacade(unityOfWork);
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);
        viendasYa.addSupplier(supplier3);

        viendasYa.addService(supplier1, new ServiceBuilder("Burguer King", supplier1).setAddressTown("Bernal").build());
        viendasYa.addService(supplier2, new ServiceBuilder("McDonalds", supplier2).setAddressTown("Bernal").build());
        viendasYa.addService(supplier3, new ServiceBuilder("Mostaza", supplier3).setAddressTown("Quilmes").build());

        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").setCategory(Category.Hamburguesa).build());
        viendasYa.addMenuToService("McDonalds", new MenuBuilder(2).setMenuName("Mc Pollo").setCategory(Category.Hamburguesa).build());
        viendasYa.addMenuToService("Mostaza", new MenuBuilder(3).setMenuName("Mostaza Whopper").setCategory(Category.Hamburguesa).build());

        // Act
        List<Menu> menusFilteredByCategory = Search.search(unityOfWork,"Whopper", Category.Hamburguesa, "Bernal");

        // Assert
        assertFalse(menusFilteredByCategory.isEmpty());
        assertEquals(menusFilteredByCategory.size(), 1);
        assertEquals(menusFilteredByCategory.get(0).getName(), "Whopper");
        assertEquals(menusFilteredByCategory.get(0).getMenuId(), 1);
    }
}
