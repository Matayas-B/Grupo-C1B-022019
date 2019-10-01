package backend.model;

import backend.model.builders.MenuBuilder;
import backend.model.builders.ServiceBuilder;
import backend.model.enums.PurchaseStatus;
import backend.model.exception.MenuNotFoundException;
import backend.model.exception.ServiceNotFoundException;
import backend.model.enums.Category;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;
import backend.repository.UnityOfWork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ViendasYaFacadeTest {

    @Test(expected = Exception.class)
    public void AddServiceToSupplierWhenHeAlreadyHasOneShouldThrowException() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        try {
            // Act
            viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                    new ArrayList<>(Arrays.asList(OfficeDays.values())),
                    new ArrayList<>(Arrays.asList(OfficeHours.values())),
                    10);

            viendasYa.addService(supplier, "Burguer New King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                    new ArrayList<>(Arrays.asList(OfficeDays.values())),
                    new ArrayList<>(Arrays.asList(OfficeHours.values())),
                    10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Supplier already has a service. Please, delete it before creating new one");
            throw ex;
        }

        Assert.fail();
    }

    @Test(expected = Exception.class)
    public void AddServiceIfOneAlreadyExistsWithThatNameShouldThrowException() throws Exception {
        // Arrange
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);

        try {
            // Act
            viendasYa.addService(supplier1, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                    new ArrayList<>(Arrays.asList(OfficeDays.values())),
                    new ArrayList<>(Arrays.asList(OfficeHours.values())),
                    10);

            viendasYa.addService(supplier2, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                    new ArrayList<>(Arrays.asList(OfficeDays.values())),
                    new ArrayList<>(Arrays.asList(OfficeHours.values())),
                    10);
        } catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Service already exists. Please, select another name.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void AddServiceShouldInitializeServiceForSupplier() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        // Act
        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        // Assert
        assertFalse(viendasYa.getAllServices().isEmpty());
        assertEquals(viendasYa.getAllServices().size(), 1);
        assertEquals(viendasYa.getAllServices().get(0).getServiceName(), "Burguer King");
        assertEquals(viendasYa.getAllServices().get(0).getSupplier().getName(), "Matayas");
        assertEquals(supplier.getService().getServiceName(), "Burguer King");
    }

    @Test(expected = Exception.class)
    public void DeleteServiceIfSupplierDoesNotHaveAnyShouldThrowException() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        try {
            // Act
            viendasYa.deleteService(supplier);
        }
        catch (Exception ex) {
            // Assert
            assertEquals(ex.getMessage(), "Supplier does not have any active service.");
            throw ex;
        }

        Assert.fail();
    }

    @Test
    public void DeleteServiceShouldDeleteServiceForSupplier() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        // Act
        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);
        viendasYa.deleteService(supplier);

        // Assert
        assertTrue(viendasYa.getAllServices().isEmpty());
    }

    @Test
    public void AddMenuToServiceShouldAddItToSupplierService() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        // Act
        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
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
    public void AddMenuToUnavailableServiceShouldThrowServiceNotFoundException() throws Exception {
        // Arrange
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
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
    public void GetAllServicesShouldReturnNotNullServicesList() throws Exception {
        // Arrange
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier3 = new SupplierUser("Brian", "Loquillo", "bra@gmail.com", "1161635613", "Canale 3134");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);
        viendasYa.addSupplier(supplier3);

        viendasYa.addService(supplier1, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);

        viendasYa.addService(supplier2, "Mcdonalds", "Test", "Quilmes", "Rivadavia 201", "Las mejores hamburguesas, mejores que cualquiera!", "mcdonalds@gmail.com", "011 51515151",
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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
    public void PurchasingLessThanTheMinimumQuantityAllowedForMenuShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
                new ArrayList<>(Arrays.asList(OfficeDays.values())),
                new ArrayList<>(Arrays.asList(OfficeHours.values())),
                10);
        viendasYa.addMenuToService("Burguer King", 1, "Whopper", "Hamburguesa de la ostia", Category.Hamburguesa, 10, LocalDate.now(), LocalDate.now(), OfficeHours.Afternoon, 15, 50, 10, 50, 10);

        customer.getAccount().depositMoney(1000);

        // Act
        Purchase purchase = viendasYa.purchase(customer, "Burguer King", 1, 10);

        // Assert
        assertFalse(viendasYa.getAllPurchases().isEmpty());
        assertEquals(viendasYa.getAllPurchases().get(0).getPurchaseId(), 1);
        assertEquals(viendasYa.getAllPurchases().get(0).getPurchaseStatus(), PurchaseStatus.InProgress);
        assertTrue(customer.getAccount().haveEnoughFunds(500));
        assertTrue(supplier.getAccount().haveEnoughFunds(500));
        assertEquals(purchase.getPurchasedMenu().getName(), "Whopper");
        assertEquals(purchase.getService().getServiceName(), "Burguer King");
        assertEquals(purchase.getCustomer().getName(), "Facundo");
        assertEquals(purchase.getCustomer().getCustomerScores().get(0).getCustomerScoreId(), 1);
        assertEquals(purchase.getCustomer().getCustomerScores().get(0).getCustomerName(), "Facundo");
        assertEquals(purchase.getCustomer().getCustomerScores().get(0).getPunctuation(), 0);
        assertFalse(purchase.getCustomer().getCustomerScores().get(0).isFinished());
    }

    @Test
    public void StartDeliveryForPurchaseShouldChangePurchaseStatusToInDelivery() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, new ServiceBuilder("Burguer King", supplier).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").build());

        customer.getAccount().depositMoney(1000);
        Purchase purchase = viendasYa.purchase(customer, "Burguer King", 1, 10);

        // Act
        viendasYa.startDeliveryForPurchase(purchase.getPurchaseId());

        // Assert
        assertFalse(viendasYa.getAllPurchases().isEmpty());
        assertEquals(viendasYa.getAllPurchases().get(0).getPurchaseStatus(), PurchaseStatus.InDelivery);
    }

    @Test
    public void FinishDeliveryForPurchaseShouldChangePurchaseStatusToFinished() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, new ServiceBuilder("Burguer King", supplier).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").build());

        customer.getAccount().depositMoney(1000);
        Purchase purchase = viendasYa.purchase(customer, "Burguer King", 1, 10);

        // Act
        viendasYa.startDeliveryForPurchase(purchase.getPurchaseId());
        viendasYa.finishDeliveryForPurchase(purchase.getPurchaseId());

        // Assert
        assertFalse(viendasYa.getAllPurchases().isEmpty());
        assertEquals(viendasYa.getAllPurchases().get(0).getPurchaseStatus(), PurchaseStatus.Finished);
    }

    @Test(expected = Exception.class)
    public void CreateMenuScoreIfUserScoreDoesNotExistsShouldThrowException() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        customer.getAccount().depositMoney(1000);

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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
        assertTrue(customer.getCustomerScores().stream().allMatch(CustomerScore::isFinished));
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

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, "Burguer King", "Test", "Quilmes", "Rivadavia 101", "Las mejores hamburguesas, lejos!", "burguerking@gmail.com", "011 51515151",
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
        assertTrue(customer.getCustomerScores().stream().allMatch(CustomerScore::isFinished));
        assertEquals(supplier.getService().getMenus().size(), 0);
        assertEquals(supplier.getService().getInvalidMenus().size(), 10);
        assertFalse(supplier.getService().getInvalidMenus().stream().noneMatch(Menu::isValidMenu));
        assertEquals(viendasYa.getSuppliers().size(), 0);
        assertEquals(viendasYa.getInvalidSuppliers().size(), 1);
        assertEquals(viendasYa.getInvalidSuppliers().get(0).getName(), "Matayas");
    }

    @Test
    public void GetHistoricalSupplierPurchasesShouldCreateHistorical() throws Exception {
        // Arrange
        CustomerUser customer = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier1 = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");
        SupplierUser supplier2 = new SupplierUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer);
        viendasYa.addSupplier(supplier1);
        viendasYa.addSupplier(supplier2);

        viendasYa.addService(supplier1, new ServiceBuilder("Burguer King", supplier1).build());
        viendasYa.addService(supplier2, new ServiceBuilder("McDonalds", supplier2).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").build());
        viendasYa.addMenuToService("McDonalds", new MenuBuilder(1).setMenuName("Mc Pollo").build());

        customer.getAccount().depositMoney(10000);
        Purchase purchase1 = viendasYa.purchase(customer, "Burguer King", 1, 10);
        viendasYa.createMenuScore(customer, "Burguer King", 1, 2);
        Purchase purchase2 = viendasYa.purchase(customer, "McDonalds", 1, 12);
        viendasYa.createMenuScore(customer, "McDonalds", 1, 4);

        // Act
        List<HistoricalPurchases> supplier2Purchases = viendasYa.getHistoricalPurchases(supplier2);

        // Assert
        assertFalse(supplier2Purchases.isEmpty());
        assertEquals(supplier2Purchases.size(), 1);
        assertEquals(supplier2Purchases.get(0).getPurchasedMenu().getName(), "Mc Pollo");
        assertEquals(supplier2Purchases.get(0).getPurchaseAmount(), 600);
        assertEquals(supplier2Purchases.get(0).getPunctuation(), 4);
    }

    @Test
    public void GetHistoricalCustomerPurchasesShouldCreateHistorical() throws Exception {
        // Arrange
        CustomerUser customer1 = new CustomerUser("Facundo", "Vigo", "facundovigo@gmail.com", "1161635613", "Canale 3134");
        CustomerUser customer2 = new CustomerUser("Juan", "Roque", "jroque@gmail.com", "1161635613", "Canale 3134");
        SupplierUser supplier = new SupplierUser("Matayas", "Beca", "matayas.beca@gmail.com", "1111111111", "Yrigoyen 313");

        ViendasYaFacade viendasYa = new ViendasYaFacade(new UnityOfWork());
        viendasYa.addCustomer(customer1);
        viendasYa.addCustomer(customer2);
        viendasYa.addSupplier(supplier);

        viendasYa.addService(supplier, new ServiceBuilder("Burguer King", supplier).build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(1).setMenuName("Whopper").build());
        viendasYa.addMenuToService("Burguer King", new MenuBuilder(2).setMenuName("BK Stacker").build());

        customer1.getAccount().depositMoney(1000);
        customer2.getAccount().depositMoney(1000);
        Purchase purchase1 = viendasYa.purchase(customer1, "Burguer King", 2, 11);
        Purchase purchase2 = viendasYa.purchase(customer2, "Burguer King", 1, 10);

        // Act
        viendasYa.createMenuScore(customer1, "Burguer King", 2, 5);
        List<HistoricalPurchases> customer1Purchases = viendasYa.getHistoricalPurchases(customer1);

        // Assert
        assertFalse(customer1Purchases.isEmpty());
        assertEquals(customer1Purchases.size(), 1);
        assertEquals(customer1Purchases.get(0).getPurchasedMenu().getName(), "BK Stacker");
        assertEquals(customer1Purchases.get(0).getPurchaseAmount(), 550);
        assertEquals(customer1Purchases.get(0).getPunctuation(), 5);
    }
}