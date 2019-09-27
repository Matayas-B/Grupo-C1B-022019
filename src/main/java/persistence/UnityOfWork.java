package persistence;

import model.*;

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
}
