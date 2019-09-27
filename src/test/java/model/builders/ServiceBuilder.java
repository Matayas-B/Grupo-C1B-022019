package model.builders;

import model.Address;
import model.Service;
import model.SupplierUser;
import model.enums.OfficeDays;
import model.enums.OfficeHours;

import java.util.Arrays;
import java.util.List;

public class ServiceBuilder {

    private int serviceId;
    private String serviceName;
    private SupplierUser supplier;
    private String icon = "Empty Icon";
    private Address address = new Address("Quilmes", "Rivadavia 101");
    private String description = "Service Test description";
    private String email = "test.service@gmail.com";
    private String phoneNumber = "11 4444 5555";
    private List<OfficeDays> officeDays = Arrays.asList(OfficeDays.values());
    private List<OfficeHours> officeHours = Arrays.asList(OfficeHours.values());
    private int deliveryDistanceKm = 10;

    public ServiceBuilder(String serviceName, SupplierUser supplier) {
        this.serviceName = serviceName;
        this.supplier = supplier;
    }

    public ServiceBuilder setAddressTown(String addressTown) {
        this.address.setTown(addressTown);
        return this;
    }

    public Service build() {
        Service service = new Service(this.serviceName, this.supplier);
        service.setIcon(this.icon);
        service.setAddress(this.address);
        service.setDescription(this.description);
        service.setEmail(this.email);
        service.setPhoneNumber(this.phoneNumber);
        service.setOfficeDays(this.officeDays);
        service.setOfficeHours(this.officeHours);
        service.setDeliveryDistance(this.deliveryDistanceKm);
        return service;
    }
}
