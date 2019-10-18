package backend.model.builders;

import backend.model.Address;
import backend.model.Service;
import backend.model.SupplierUser;
import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;

import java.util.Arrays;
import java.util.List;

public class ServiceBuilder {

    private long serviceId;
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

    public ServiceBuilder(long serviceId, String serviceName, SupplierUser supplier) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.supplier = supplier;
    }

    public ServiceBuilder setAddressTown(String addressTown) {
        this.address.setTown(addressTown);
        return this;
    }

    public Service build() {
        Service service = new Service();
        service.setServiceId(this.serviceId);
        service.setServiceName(this.serviceName);
        service.setSupplier(this.supplier);
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
