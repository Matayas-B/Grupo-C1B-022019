package backend.controller.requests;

import backend.model.enums.OfficeDays;
import backend.model.enums.OfficeHours;

import javax.validation.constraints.*;
import java.util.List;

public class ServiceRequest {
    @Positive
    long supplierId;
    @PositiveOrZero
    long serviceId;
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

    public long getServiceId() {
        return serviceId;
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

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setAddressTown(String addressTown) {
        this.addressTown = addressTown;
    }

    public void setAddressLocation(String addressLocation) {
        this.addressLocation = addressLocation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setOfficeDays(List<OfficeDays> officeDays) {
        this.officeDays = officeDays;
    }

    public void setOfficeHours(List<OfficeHours> officeHours) {
        this.officeHours = officeHours;
    }

    public void setDeliveryDistance(int deliveryDistance) {
        this.deliveryDistance = deliveryDistance;
    }
}
