package backend.model;

import backend.model.enums.Category;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue(value = "supplier")
public class SupplierUser extends User {

    @Transient
    private Service service;

    public Service getService() {
        return service;
    }

    public MoneyAccount getAccount() {
        return super.getAccount();
    }

    public SupplierUser(String name, String lastName, String eMail, String phone, String address) {
        super(name, lastName, eMail, phone, address);
    }

    public boolean hasService() {
        return service != null;
    }

    public void addService(String serviceName, String icon, Address address, String description, String email, String phoneNumber, List<OfficeDays> officeDays, List<OfficeHours> officeHours, int deliveryDistance) {
        service = new Service(serviceName, icon, address, description, email, phoneNumber, officeDays, officeHours, deliveryDistance, this);
    }

    public void addMenu(int id, String name, String description, Category category, int deliveryFee, LocalDate startDate, LocalDate endDate, OfficeHours deliveryHours, int averageDeliveryMinutes, int price, int minQuantity, int minQuantityPrice, int maxDailySales) {
        service.addMenu(id, name, description, category, deliveryFee, startDate, endDate, deliveryHours, averageDeliveryMinutes, price, minQuantity, minQuantityPrice, maxDailySales);
    }

    public void deleteService() {
        service = null;
    }
}
