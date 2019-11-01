package backend.controller.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class NewScorePunctuationRequest {
    @NotNull(message = "CustomerId should not be null.")
    private long customerId;
    @NotNull(message = "ServiceId should not be null.")
    private long serviceId;
    @NotNull(message = "MenuId should not be null.")
    private long menuId;
    @Min(value = 1, message = "Score should be between 1 to 5.")
    @Max(value = 5, message = "Score should be between 1 to 5.")
    private int punctuation;

    public long getCustomerId() {
        return customerId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public long getMenuId() {
        return menuId;
    }

    public int getPunctuation() {
        return punctuation;
    }

    public String toString() {
        return String.format("Customer id: %d, Service id: %d, Menu id: %d, Punctuation: %d", customerId, serviceId, menuId, punctuation);
    }
}
