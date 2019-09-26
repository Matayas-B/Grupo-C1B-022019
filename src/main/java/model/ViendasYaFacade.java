package model;

import model.exception.MenuNotFoundException;
import model.exception.ServiceNotFoundException;
import model.enums.Category;
import model.enums.OfficeDays;
import model.enums.OfficeHours;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ViendasYaFacade {

    public List<SupplierUser> suppliers = new ArrayList<>();
    public List<CustomerUser> customers = new ArrayList<>();
    public List<Purchase> purchases = new ArrayList<>();

    public void addSupplier(SupplierUser supplier) {
        suppliers.add(supplier);
    }

    public void addCustomer(CustomerUser customer) {
        customers.add(customer);
    }

    public void addService(SupplierUser supp, String serviceName, String icon, String address, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance) {
        supp.addService(serviceName, icon, address, description, email, phoneNumber, officeDays, officeHours, deliveryDistance);
    }

    public void addMenuToService(String serviceName, int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        SupplierUser supplier = FindSupplierWithServiceName(serviceName);

        if (supplier == null)
            throw new ServiceNotFoundException();

        supplier.addMenu(id, name, description, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales);
    }

    public List<Service> getAllServices() {
        return suppliers.stream().map(SupplierUser::getService).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Purchase> getAllPurchases() {
        return purchases;
    }

    public Purchase purchase(CustomerUser customer, String serviceName, int menuId, int quantity) throws Exception {
        Service currentService = FindServiceByName(serviceName);
        if (currentService == null)
            throw new ServiceNotFoundException();

        Menu currentMenu = currentService.getMenuByMenuId(menuId);
        if (currentMenu == null)
            throw new MenuNotFoundException();

        if (currentMenu.getMinQuantity() > quantity)
            throw new Exception(String.format("You cannot buy less than %s units.", currentMenu.getMinQuantity()));

        List<Purchase> purchasedMenus = GetAllPurchasesForMenu(currentService, currentMenu);
        if (purchasedMenus.size() >= currentMenu.getMaxDailySales())
            throw new Exception("Maximun number of sales per day have been reached for this menu.");

        if (!customer.getAccount().haveEnoughFunds(currentMenu.getPrice() * quantity))
            throw new Exception("Customer does not have enough funds to purchase that quantity.");

        customer.getAccount().extractMoney(currentMenu.getPrice() * quantity);

        Purchase newPurchase = new Purchase(customer, currentService, currentMenu, LocalDate.now());

        currentService.getSupplier().getAccount().depositMoney(currentMenu.getPrice() * quantity);

        purchases.add(newPurchase);
        return newPurchase;
    }

    /* Private Methods */
    private SupplierUser FindSupplierWithServiceName(String serviceName) {
        return suppliers.stream().filter(s -> s.getService().getServiceName().equals(serviceName)).findFirst().orElse(null);
    }

    private Service FindServiceByName(String serviceName) {
        return getAllServices().stream().filter(s -> s.getServiceName().equals(serviceName)).findFirst().orElse(null);
    }

    private List<Purchase> GetAllPurchasesForMenu(Service currentService, Menu currentMenu) {
        List<Purchase> todayPurchases = purchases.stream().filter(p -> p.getPurchasedDate().equals(LocalDate.now())).collect(Collectors.toList());
        return todayPurchases.stream().filter(p -> p.getService().equals(currentService) && p.getPurchasedMenu().equals(currentMenu)).collect(Collectors.toList());
    }
}