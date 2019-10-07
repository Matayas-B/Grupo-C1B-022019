package backend.controller.requests;

import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;

import javax.persistence.Transient;
import java.util.List;

public class NewServiceRequest {
    long supplierId;
    String serviceName;
    String icon;
    String addressTown;
    String addressLocation;
    String description;
    String email;
    String phoneNumber;
    List<OfficeDays> officeDays;
    List<OfficeHours> officeHours;
    int deliveryDistance;

    public long getSupplierId() {
        return supplierId;
    }

    public List<OfficeDays> getOfficeDays() {
        return officeDays;
    }

    public List<OfficeHours> getOfficeHours() {
        return officeHours;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getIcon() {
        return icon;
    }

    public String getAddressTown() {
        return addressTown;
    }

    public String getAddressLocation() {
        return addressLocation;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getDeliveryDistance() {
        return deliveryDistance;
    }
}
