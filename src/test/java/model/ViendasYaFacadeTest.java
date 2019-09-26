package model;

import model.builders.MenuBuilder;
import model.exception.MenuNotFoundException;
import model.exception.ServiceNotFoundException;
import model.enums.Category;
import model.enums.OfficeDays;
import model.enums.OfficeHours;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
public class ViendasYaFacadeTest {

    @Test
    public void AddServiceShouldInitializeServiceForSupplier() {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addSupplier(supplier);

        // Act
        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<OfficeDays>(Arrays.asList(OfficeDays.values())),
                new ArrayList<OfficeHours>(Arrays.asList(OfficeHours.values())),
                10);

        // Assert
        assertFalse(viendasYa.getAllServices().isEmpty());
        assertEquals(viendasYa.getAllServices().size(), 1);
        assertEquals(viendasYa.getAllServices().get(0).getServiceName(), "Burguer King");
        assertEquals(viendasYa.getAllServices().get(0).getSupplier().getName(), "Matayas");
        assertEquals(supplier.getService().getServiceName(), "Burguer King");
    }

    @Test
    public void AddMenuToServiceShouldAddItToSupplierService() {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addSupplier(supplier);

        // Act
        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<OfficeDays>(Arrays.asList(OfficeDays.values())),
                new ArrayList<OfficeHours>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 1, 50, 100);

        // Assert
        assertFalse(viendasYa.getAllServices().isEmpty());
        assertEquals(viendasYa.getAllServices().size(), 1);
        assertEquals(viendasYa.getAllServices().get(0).getServiceName(), "Burguer King");
        assertEquals(viendasYa.getAllServices().get(0).getSupplier().getName(), "Matayas");
        assertEquals(viendasYa.getAllServices().get(0).getMenus().size(), 1);
        assertEquals(viendasYa.getAllServices().get(0).getMenus().get(0).getName(), "Whopper");
        assertEquals(supplier.getService().getServiceName(), "Burguer King");
    }

    @Test(expected = ServiceNotFoundException.class)
    public void AddMenuToUnavailableServiceShouldThrowServiceNotFoundException() {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<OfficeDays>(Arrays.asList(OfficeDays.values())),
                new ArrayList<OfficeHours>(Arrays.asList(OfficeHours.values())),
                10);

        try {
            // Act
            viendasYa.addMenuToService("McDonalds", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 1, 50, 100);
        } catch (ServiceNotFoundException ex) {
            // Assert
            assertEquals(ex.getMessage(), "Service does not longer exists.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void GetAllServicesShouldReturnNotNullServicesList() {
        // Arrange
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier3 = new SupplierUser("Brian", "Loquillo", "bra@gmail.com", "1161635613", "Canale 3134");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);
        viendasYa.addSupplier(supplier3);

        viendasYa.addService(supplier1, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addService(supplier2, "Mcdonalds", "Test", "Rivadavia 201", "Las mejores hamburguesas, mejores que cualquiera!", "mcdonalds@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        // Act
        List<Service> allServices = viendasYa.getAllServices();

        // Assert
        assertFalse(allServices.isEmpty());
        assertEquals(allServices.size(), 2);
    }

    @Test(expected = ServiceNotFoundException.class)
    public void PurchasingMenuFromUnavailableServiceShouldThrowServiceNotFoundException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 1);
        } catch (ServiceNotFoundException ex) {
            // Assert
            assertEquals(ex.getMessage(), "Service does not longer exists.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = MenuNotFoundException.class)
    public void PurchasingNonExistingMenuShouldThrowMenuNotFoundException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 1);
        } catch (MenuNotFoundException ex) {
            // Assert
            assertEquals(ex.getMessage(), "Menu does not longer exists.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void PurchasingLessThanTheMinimunQuantityAllowedForMenuShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 0);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 1);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "You cannot buy less than 10 units.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void PurchasingMoreThanTheMaximumNumberOfSalesAllowedForMenuShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 0);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Maximun number of sales per day have been reached for this menu.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void PurchasingWithLessFundsThatTheRequiredShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 10);

        customer.getAccount().depositMoney(10);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Customer does not have enough funds to purchase that quantity.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void PurchasingWithPendingScoresShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 100);

        customer.getAccount().depositMoney(1000);
        Purchase purchase = viendasYa.purchase(customer, "Burguer King", 1, 10);

        try {
            // Act
            viendasYa.purchase(customer, "Burguer King", 1, 10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Customer has pending scores to punctuate before purchasing.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void BuyMenuFromServiceShouldCreatePurchase() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);
        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 10);

        customer.getAccount().depositMoney(1000);

        // Act
        Purchase purchase = viendasYa.purchase(customer, "Burguer King", 1, 10);

        // Assert
        assertFalse(viendasYa.getAllPurchases().isEmpty());
        assertTrue(customer.getAccount().haveEnoughFunds(500));
        assertTrue(supplier.getAccount().haveEnoughFunds(500));
        assertEquals(purchase.getPurchasedMenu().getName(), "Whopper");
        assertEquals(purchase.getService().getServiceName(), "Burguer King");
        assertEquals(purchase.getCustomer().getName(), "Facundo");
        assertEquals(purchase.getCustomer().getUserScores().get(0).getCustomerName(), "Facundo");
        assertEquals(purchase.getCustomer().getUserScores().get(0).getPunctuation(), 0);
        assertFalse(purchase.getCustomer().getUserScores().get(0).isFinished());
    }

    @Test(expected = Exception.class)
    public void CreateMenuScoreIfUserScoreDoesNotExistsShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        customer.getAccount().depositMoney(1000);

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        try {
            viendasYa.createMenuScore(customer, "Burguer King", 1, 1);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "User Score does not exists.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void CreateMenuScoreShouldDeleteMenuIfAverageIsBelow2() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        customer.getAccount().depositMoney(10000);

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);
        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 1000);

        for (int i = 0; i < 19; i++) {
            viendasYa.purchase(customer, "Burguer King", 1, 10);
            viendasYa.createMenuScore(customer, "Burguer King", 1, 1);
        }

        // Act
        viendasYa.purchase(customer, "Burguer King", 1, 10);
        viendasYa.createMenuScore(customer, "Burguer King", 1, 1);

        // Assert
        assertTrue(customer.getUserScores().stream().allMatch(UserScore::isFinished));
        assertEquals(supplier.getService().getMenus().size(), 0);
        assertEquals(supplier.getService().getInvalidMenus().size(), 1);
        assertFalse(supplier.getService().getInvalidMenus().get(0).isValidMenu());
    }

    @Test
    public void CreateMenuScoreShouldRemoveSupplierIfItHasMoreThan9InvalidMenus() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        customer.getAccount().depositMoney(10000);

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 1000);

        for (int i = 2; i < 11; i++) {
            supplier.getService().getInvalidMenus().add(new MenuBuilder(i).build());
        }

        for (int i = 0; i < 19; i++) {
            viendasYa.purchase(customer, "Burguer King", 1, 10);
            viendasYa.createMenuScore(customer, "Burguer King", 1, 1);
        }

        // Act
        viendasYa.purchase(customer, "Burguer King", 1, 10);
        viendasYa.createMenuScore(customer, "Burguer King", 1, 1);

        // Assert
        assertTrue(customer.getUserScores().stream().allMatch(UserScore::isFinished));
        assertEquals(supplier.getService().getMenus().size(), 0);
        assertEquals(supplier.getService().getInvalidMenus().size(), 10);
        assertFalse(supplier.getService().getInvalidMenus().stream().allMatch(im -> !im.isValidMenu()));
        assertEquals(viendasYa.suppliers.size(), 0);
        assertEquals(viendasYa.invalidSuppliers.size(), 1);
        assertEquals(viendasYa.invalidSuppliers.get(0).getName(), "Matayas");
    }
}