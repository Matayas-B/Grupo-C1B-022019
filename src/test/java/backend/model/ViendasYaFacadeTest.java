package backend.model;

import backend.model.builders.MenuBuilder;
import backend.model.builders.ServiceBuilder;
import backend.model.enums.Category;
import backend.model.enums.PurchaseStatus;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;
import backend.model.exception.MaxNumberOfMenusAllowedException;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ViendasYaFacadeTest {

    @Test(expected = Exception.class)
    public void AddMenuToServiceWhenItHasAlreadyTwentyMenusShouldThrowException() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        ViendasYaFacade viendasYa = new ViendasYaFacade();

        for (int i = 0; i < 20; i++) {
            Menu nextMenu = new MenuBuilder(i).build();
            viendasYa.addMenuToService(service, nextMenu.getName(), nextMenu.getDescription(), nextMenu.getImageUrl(), nextMenu.getCategory(), nextMenu.getDeliveryFee(), nextMenu.getStartDate(), nextMenu.getEndDate(), nextMenu.getDeliveryHours(), nextMenu.getAverageDeliveryMinutes(), nextMenu.getPrice(), nextMenu.getMinQuantity(), nextMenu.getMinQuantityPrice(), nextMenu.getMaxDailySales());
        }

        try {
            // Act
            viendasYa.addMenuToService(service, "Whopper", "A terrific hamburguer", "", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(),
                    OfficeHours.Afternoon, 10, 100, 10, 100, 10);
        } catch (MaxNumberOfMenusAllowedException ex) {
            // Assert
            assertEquals(ex.getMessage(), "Service with id 1 has reached the maximum number of allowed menus.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void AddMenuToServiceShouldIncludeThatMenuOnValidList() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        ViendasYaFacade viendasYa = new ViendasYaFacade();

        // Act
        viendasYa.addMenuToService(service, "Whopper", "A terrific hamburguer", "", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(),
                OfficeHours.Afternoon, 10, 100, 10, 100, 10);

        assertFalse(service.getValidMenus().isEmpty());
        assertEquals(service.getValidMenus().size(), 1);
        assertEquals(service.getValidMenus().get(0).getName(), "Whopper");
    }

    @Test(expected = Exception.class)
    public void AddServiceToSupplierWhenHeAlreadyHasOneShouldThrowException() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        try {
            // Act
            viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                    Arrays.asList(OfficeDays.values()),
                    Arrays.asList(OfficeHours.values()),
                    10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Supplier already has a service. Please, delete it before creating new one");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void AddServiceShouldInitializeServiceForSupplier() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade();

        // Act
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        // Assert
        assertNotNull(supplier.getService());
        assertEquals(supplier.getService().getServiceName(), "Burguer King");
        assertTrue(supplier.getService().isValidService());
    }

    @Test(expected = Exception.class)
    public void PurchasingLessThanTheMinimumQuantityAllowedForMenuShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);

        try {
            // Act
            viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 1, new CustomerScore());
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "You cannot buy less than 10 units.");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void PurchasingWithLessFundsThatTheRequiredShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);
        customer.getAccount().depositMoney(10);

        try {
            // Act
            viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, new CustomerScore());
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
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);
        customer.getAccount().depositMoney(1000);
        customer.addDefaultScore((long) 1, (long) 1);

        try {
            // Act
            viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, new CustomerScore());
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Customer has pending scores to punctuate before purchasing.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void PurchaseMenuFromServiceShouldCreatePurchase() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);
        customer.getAccount().depositMoney(1000);

        // Act
        Purchase purchase = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId()));

        // Assert
        assertTrue(customer.getAccount().haveEnoughFunds(500));
        assertTrue(supplier.getAccount().haveEnoughFunds(500));
        assertEquals(purchase.getPurchaseStatus(), PurchaseStatus.InProgress);
        assertEquals(purchase.getPurchasedMenu().getName(), "Whopper");
        assertEquals(purchase.getService().getServiceName(), "Burguer King");
        assertEquals(purchase.getCustomer().getName(), "Facundo");
        assertEquals(purchase.getCustomer().getCustomerScores().get(0).getCustomerEmail(), "facundovigo@gmail.com");
        assertEquals(purchase.getCustomer().getCustomerScores().get(0).getPunctuation(), 0);
        assertFalse(purchase.getCustomer().getCustomerScores().get(0).isFinished());
    }

    @Test(expected = Exception.class)
    public void CreateMenuScoreIfUserScoreDoesNotExistsShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        customer.getAccount().depositMoney(1000);

        try {
            // Act
            viendasYa.createMenuScore(customer, service, menu, 1);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "User Score does not exists.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void CreateMenuScoreShouldCreateAndReturnIt() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        customer.getAccount().depositMoney(1000);
        customer.addDefaultScore((long) 1, (long) 1);

        // Act
        MenuScore menuScore = viendasYa.createMenuScore(customer, service, menu, 1);

        // Assert
        assertTrue(customer.getCustomerScores().get(0).isFinished());
        assertEquals(menuScore.getCustomerEmail(), customer.getEmail());
        assertEquals(menuScore.getPunctuation(), 1);
    }

    @Test
    public void CheckMenuAndServiceValidityShouldMarkMenuAsInvalid() {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        service.getValidMenus().add(menu);

        for (int i = 0; i < 20; i++) {
            menu.addScore(customer.getEmail(), 1);
        }

        ViendasYaFacade viendasYa = new ViendasYaFacade();

        // Act
        viendasYa.checkMenuAndServiceValidity(service, menu);

        // Assert
        assertTrue(service.getValidMenus().isEmpty());
        assertEquals(service.getInvalidMenus().size(), 1);
        assertFalse(menu.isValidMenu());
    }

    @Test
    public void CheckMenuAndServiceValidityShouldMarkServiceAsInvalid() {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();

        for (int i = 0; i < 10; i++) {
            Menu menu = new MenuBuilder(1).setMenuName(String.format("Menu %d", i)).build();
            service.getValidMenus().add(menu);
        }

        for (Menu menu : service.getValidMenus()) {
            for (int i = 0; i < 20; i++) {
                menu.addScore(customer.getEmail(), 1);
            }
        }

        ViendasYaFacade viendasYa = new ViendasYaFacade();

        // Act
        for (int i = 0; i < 10; i++) {
            viendasYa.checkMenuAndServiceValidity(service, service.getValidMenus().get(0));
        }

        // Assert
        assertTrue(service.getValidMenus().isEmpty());
        assertFalse(service.isValidService());
        assertEquals(service.getInvalidMenus().size(), 10);
        for (Menu menu : service.getInvalidMenus()) {
            assertFalse(menu.isValidMenu());
        }
    }

    @Test
    public void StartDeliveryForPurchaseForMarkItAsInDelivery() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);
        customer.getAccount().depositMoney(1000);

        Purchase purchase = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId()));

        // Act
        viendasYa.startDeliveryForPurchase(purchase);

        // Assert
        assertEquals(purchase.getPurchaseStatus(), PurchaseStatus.InDelivery);
    }

    @Test
    public void FinishDeliveryForPurchaseForMarkItAsFinished() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        viendasYa.addServiceToSupplier(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                Arrays.asList(OfficeDays.values()),
                Arrays.asList(OfficeHours.values()),
                10);

        supplier.getService().setServiceId((long) 1);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        supplier.getService().getValidMenus().add(menu);
        customer.getAccount().depositMoney(1000);

        Purchase purchase = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId()));
        viendasYa.startDeliveryForPurchase(purchase);

        // Act
        viendasYa.finishDeliveryForPurchase(purchase);

        // Assert
        assertEquals(purchase.getPurchaseStatus(), PurchaseStatus.Finished);
    }

    @Test
    public void GetSupplierHistoricalPurchasesShouldReturnHistoricalList() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        customer.getAccount().depositMoney(1000);

        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        supplier.setService(service);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        service.getValidMenus().add(menu);

        ViendasYaFacade viendasYa = new ViendasYaFacade();
        CustomerScore customerScore = new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId());
        customerScore.setCustomerScoreId((long) 1);
        Purchase purchase = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, customerScore);
        purchase.setPurchaseId((long) 1);
        viendasYa.createMenuScore(customer, service, menu, 2);

        // Act
        List<HistoricalPurchases> supplierHistoricalPurchases = viendasYa.getSupplierHistoricalPurchases(Collections.singletonList(purchase));

        // Assert
        assertFalse(supplierHistoricalPurchases.isEmpty());
        assertEquals(supplierHistoricalPurchases.size(), 1);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchasedDate(), purchase.getPurchasedDate());
        assertEquals(supplierHistoricalPurchases.get(0).getPurchaseStatus(), PurchaseStatus.InProgress);
        assertEquals(supplierHistoricalPurchases.get(0).getPunctuation(), 2);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchasedMenu(), menu);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchaseAmount(), 500);
    }

    @Test
    public void GetCustomerHistoricalPurchasesShouldReturnHistoricalList() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "facuvigo123", "1161635613", "Canale 3134");
        customer.getAccount().depositMoney(1500);

        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "matayas123", "1111111111", "Yrigoyen 313");
        Service service = new ServiceBuilder(1, "Burguer King", supplier).build();
        supplier.setService(service);
        Menu menu = new MenuBuilder(1).setMenuName("Whopper").build();
        service.getValidMenus().add(menu);

        ViendasYaFacade viendasYa = new ViendasYaFacade();

        // First Purchase
        CustomerScore customerScore1 = new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId());
        customerScore1.setCustomerScoreId((long) 1);
        Purchase purchase1 = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 10, customerScore1);
        purchase1.setPurchaseId((long) 1);
        viendasYa.createMenuScore(customer, service, menu, 2);

        // Second Purchase
        CustomerScore customerScore2 = new CustomerScore(customer.getEmail(), supplier.getService().getServiceId(), menu.getMenuId());
        customerScore2.setCustomerScoreId((long) 2);
        Purchase purchase2 = viendasYa.purchaseMenu(customer, supplier.getService(), supplier.getService().getMenuByMenuId(1), 11, customerScore2);
        purchase2.setPurchaseId((long) 2);
        viendasYa.createMenuScore(customer, service, menu, 5);

        // Act
        List<HistoricalPurchases> supplierHistoricalPurchases = viendasYa.getSupplierHistoricalPurchases(Arrays.asList(purchase1, purchase2));

        // Assert
        assertFalse(supplierHistoricalPurchases.isEmpty());
        assertEquals(supplierHistoricalPurchases.size(), 2);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchasedDate(), purchase1.getPurchasedDate());
        assertEquals(supplierHistoricalPurchases.get(0).getPurchaseStatus(), PurchaseStatus.InProgress);
        assertEquals(supplierHistoricalPurchases.get(0).getPunctuation(), 2);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchasedMenu(), menu);
        assertEquals(supplierHistoricalPurchases.get(0).getPurchaseAmount(), 500);
        assertEquals(supplierHistoricalPurchases.get(1).getPurchasedDate(), purchase2.getPurchasedDate());
        assertEquals(supplierHistoricalPurchases.get(1).getPurchaseStatus(), PurchaseStatus.InProgress);
        assertEquals(supplierHistoricalPurchases.get(1).getPunctuation(), 5);
        assertEquals(supplierHistoricalPurchases.get(1).getPurchasedMenu(), menu);
        assertEquals(supplierHistoricalPurchases.get(1).getPurchaseAmount(), 550);
    }
}