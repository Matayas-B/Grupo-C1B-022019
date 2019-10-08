package backend.controller.requests;

import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

public class NewServiceRequest {
    @Positive
    long supplierId;
    String icon;
    @NotEmpty(message = "Please, provide a name to the service.")
    String serviceName;
    @NotEmpty(message = "Select the town for your service.")
    String addressTown;
    @NotEmpty(message = "Provide an address for your service.")
    String addressLocation;
    @NotEmpty(message = "Describe what your service is about ! ! !")
    String description;
    @Email(message = "Your service must have a valid email.")
    String email;
    String phoneNumber;
    @NotEmpty
    List<OfficeDays> officeDays;
    @NotEmpty
    List<OfficeHours> officeHours;
    @Min(1)
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
