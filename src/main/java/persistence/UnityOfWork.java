package persistence;

import model.*;
import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UnityOfWork {
    public List<SupplierUser> suppliers = new ArrayList<>();
    public List<SupplierUser> invalidSuppliers = new ArrayList<>();
    public List<CustomerUser> customers = new ArrayList<>();
    public List<Purchase> purchases = new ArrayList<>();

    public List<Service> getAllServices() {
        return suppliers.stream().map(SupplierUser::getService).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<Menu> getAllMenus() {
        return suppliers.stream().map(SupplierUser::getService).map(Service::getMenus).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public boolean isServiceAlreadyCreated(String serviceName) {
        return getAllServices().stream().anyMatch(s -> s.getServiceName().equals(serviceName));
    }

    public Purchase getPurchaseById(int purchaseId) {
        return purchases.stream().filter(p -> p.getPurchaseId() == purchaseId).findFirst().orElse(null);
    }

    public void savePurchase(Purchase purchase) {
        purchases.set(purchases.indexOf(purchase), purchase);
    }

    public Purchase addPurchase(CustomerUser customer, int customerScoreId, Service service, Menu purchasedMenu, LocalDate dateOfPurchase, int purchaseAmount) {
        int newPurchaseId = purchases.size() + 1;
        Purchase newPurchase = new Purchase(newPurchaseId, customer, customerScoreId, service, purchasedMenu, LocalDate.now(), purchaseAmount);
        purchases.add(newPurchase);
        return newPurchase;
    }
}
