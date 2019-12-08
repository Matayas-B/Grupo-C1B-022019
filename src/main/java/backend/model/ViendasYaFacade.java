package backend.model;

import backend.model.enums.Category;
import backend.model.exception.ExistingServiceException;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;
import backend.model.exception.MaxNumberOfMenusAllowedException;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

public class ViendasYaFacade {

    public ViendasYaFacade() { }

    public void addMenuToService(Service service, String name, String description, String imageurl, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        if (service.getValidMenus().size() >= 20)
            throw new MaxNumberOfMenusAllowedException(service.getServiceId());

        service.addMenu(name, description, imageurl, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales);
    }

    public void addServiceToSupplier(SupplierUser supp, String serviceName, String icon, String addressTown, String addressLocation, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance) throws Exception {
        if (supp.hasService())
            throw new ExistingServiceException();

        supp.addService(serviceName, icon, new Address(addressTown, addressLocation), description, email, phoneNumber, officeDays, officeHours, deliveryDistance);
    }

    public Purchase purchaseMenu(CustomerUser customer, Service service, Menu menu, int quantity, CustomerScore customerScore) throws Exception {
        if (menu.getMinQuantity() > quantity)
            throw new Exception(String.format("You cannot buy less than %s units.", menu.getMinQuantity()));

        if (!customer.getAccount().haveEnoughFunds(menu.getPrice() * quantity))
            throw new Exception("Customer does not have enough funds to purchase that quantity.");

        if (customer.hasPendingPunctuations())
            throw new Exception("Customer has pending scores to punctuate before purchasing.");

        int purchasedAmount = menu.getPrice() * quantity;
        customer.getAccount().extractMoney(purchasedAmount);
        service.getSupplier().getAccount().depositMoney(purchasedAmount);
        customer.getCustomerScores().add(customerScore);

        return new Purchase(customer, customerScore, service, menu, LocalDate.now(), purchasedAmount);
    }

    public MenuScore createMenuScore(CustomerUser customer, Service service, Menu menu, int punctuation) throws Exception {
        CustomerScore customerScore = customer.findUserScore(service.getServiceId(), menu.getMenuId());
        customerScore.setPunctuation(punctuation);
        return menu.addScore(customerScore.getCustomerEmail(), punctuation);
    }

    public void checkMenuAndServiceValidity(Service service, Menu menu) {
        if (menu.hasEnoughScores() && menu.getScoreAverage() < 2) {
            service.markMenuAsInvalid(menu);
            if (service.getInvalidMenus().size() == 10) {
                service.markServiceAsInvalid();
                markSupplierAsInvalid(service.getSupplier());
            }
        }
    }

    public void startDeliveryForPurchase(Purchase purchase) {
        purchase.startDelivery();
    }

    public void finishDeliveryForPurchase(Purchase purchase) {
        purchase.finishDelivery();
    }

    public List<HistoricalPurchases> getSupplierHistoricalPurchases(List<Purchase> supplierPurchases) {
        return supplierPurchases.stream().map(sp -> new HistoricalPurchases(sp.getPurchasedDate(), sp.getPurchaseId(), sp.getPurchaseStatus(), getPunctuationForPurchase(sp), sp.getPurchasedMenu(), sp.getPurchaseAmount()))
                .collect(Collectors.toList());
    }

    public List<HistoricalPurchases> getCustomerHistoricalPurchases(List<Purchase> customerPurchases) {
        return customerPurchases.stream().map(cp -> new HistoricalPurchases(cp.getPurchasedDate(), cp.getPurchaseId(), cp.getPurchaseStatus(), getPunctuationForPurchase(cp), cp.getPurchasedMenu(), cp.getPurchaseAmount()))
                .collect(Collectors.toList());
    }

    /* Private Methods */
    private int getPunctuationForPurchase(Purchase purchase) {
        return purchase.getCustomer().getCustomerScoreById(purchase.getCustomerScore().getCustomerScoreId()).getPunctuation();
    }

    private void markSupplierAsInvalid(SupplierUser invalidSupplier) {
        invalidSupplier.setValidSupplier(false);
    }
}