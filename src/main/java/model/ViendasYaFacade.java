package model;

import model.exception.MenuNotFoundException;
import model.exception.ServiceNotFoundException;
import model.enums.Category;
import model.enums.OfficeDays;
import model.enums.OfficeHours;
import org.joda.time.LocalDate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ViendasYaFacade {

    public List<SupplierUser> suppliers = new ArrayList<>();
    public List<SupplierUser> invalidSuppliers = new ArrayList<>();
    public List<CustomerUser> customers = new ArrayList<>();
    public List<Purchase> purchases = new ArrayList<>();

    public void addSupplier(SupplierUser supplier) {
        suppliers.add(supplier);
    }

    public void addCustomer(CustomerUser customer) {
        customers.add(customer);
    }

    public void addService(SupplierUser supp, String serviceName, String icon, String addressTown, String addressLocation, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance) {
        supp.addService(serviceName, icon, new Address(addressTown, addressLocation), description, email, phoneNumber, officeDays, officeHours, deliveryDistance);
    }

    public void addMenuToService(String serviceName, int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        SupplierUser supplier = findSupplierWithServiceName(serviceName);

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
        Service currentService = findServiceByName(serviceName);
        if (currentService == null)
            throw new ServiceNotFoundException();

        Menu currentMenu = currentService.getMenuByMenuId(menuId);
        if (currentMenu == null)
            throw new MenuNotFoundException();

        if (currentMenu.getMinQuantity() > quantity)
            throw new Exception(String.format("You cannot buy less than %s units.", currentMenu.getMinQuantity()));

        List<Purchase> purchasedMenus = getAllPurchasesForMenu(currentService, currentMenu);
        if (purchasedMenus.size() >= currentMenu.getMaxDailySales())
            throw new Exception("Maximun number of sales per day have been reached for this menu.");

        if (!customer.getAccount().haveEnoughFunds(currentMenu.getPrice() * quantity))
            throw new Exception("Customer does not have enough funds to purchase that quantity.");

        if (customer.hasPendingPunctuations())
            throw new Exception("Customer has pending scores to punctuate before purchasing.");

        customer.getAccount().extractMoney(currentMenu.getPrice() * quantity);
        Purchase newPurchase = new Purchase(customer, currentService, currentMenu, LocalDate.now());
        currentService.getSupplier().getAccount().depositMoney(currentMenu.getPrice() * quantity);
        customer.addDefaultScore(currentService, currentMenu);

        purchases.add(newPurchase);
        return newPurchase;
    }

    public void createMenuScore(CustomerUser customer, String serviceName, int menuId, int punctuation) throws Exception {
        UserScore userScore = customer.findUserScore(serviceName, menuId);
        if (userScore == null)
            throw new Exception("User Score does not exists.");

        userScore.setPunctuation(punctuation);
        userScore.getMenu().addScore(userScore.getCustomerName(), punctuation);

        if (userScore.getMenu().hasEnoughScores() && userScore.getMenu().getScoreAverage() < 2) {
            Service service = userScore.getService();
            service.markMenuAsInvalid(userScore.getMenu());

            if (service.getInvalidMenus().size() == 10) {
                markSupplierAsInvalid(service.getSupplier());
            }
        }
    }

    public List<Menu> search() {
        // TODO: Missing search functionality. Customer should be able to filter Menus by name, category or addres.
        return new ArrayList<Menu>();
    }

    /* Private Methods */
    private void markSupplierAsInvalid(SupplierUser invalidSupplier) {
        suppliers.remove(invalidSupplier);
        invalidSuppliers.add(invalidSupplier);
    }

    private SupplierUser findSupplierWithServiceName(String serviceName) {
        return suppliers.stream().filter(s -> s.getService().getServiceName().equals(serviceName)).findFirst().orElse(null);
    }

    private Service findServiceByName(String serviceName) {
        return getAllServices().stream().filter(s -> s.getServiceName().equals(serviceName)).findFirst().orElse(null);
    }

    private List<Purchase> getAllPurchasesForMenu(Service currentService, Menu currentMenu) {
        List<Purchase> todayPurchases = purchases.stream().filter(p -> p.getPurchasedDate().equals(LocalDate.now())).collect(Collectors.toList());
        return todayPurchases.stream().filter(p -> p.getService().equals(currentService) && p.getPurchasedMenu().equals(currentMenu)).collect(Collectors.toList());
    }
}