package backend.repository;

import backend.model.*;
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

    public List<Purchase> getPurchasesForSupplier(SupplierUser supplier) {
        return purchases.stream().filter(p -> p.getService().getSupplier().equals(supplier)).collect(Collectors.toList());
    }

    public List<Purchase> getPurchasesForCustomer(CustomerUser customer) {
        return purchases.stream().filter(p -> p.getCustomer().equals(customer)).collect(Collectors.toList());
    }

    public Purchase addPurchase(CustomerUser customer, CustomerScore customerScore, Service service, Menu purchasedMenu, LocalDate dateOfPurchase, int purchaseAmount) {
        Purchase newPurchase = new Purchase(customer, customerScore, service, purchasedMenu, LocalDate.now(), purchaseAmount);
        purchases.add(newPurchase);
        return newPurchase;
    }
}
