package backend.controller.requests;

public class PurchaseRequest {
    long customerId;
    long serviceId;
    long menuId;
    int quantity;

    public long getCustomerId() {
        return customerId;
    }

    public long getServiceId() {
        return serviceId;
    }

    public long getMenuId() {
        return menuId;
    }

    public int getQuantity() {
        return quantity;
    }
}
