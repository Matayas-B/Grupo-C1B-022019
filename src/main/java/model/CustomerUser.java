package model;

import java.util.ArrayList;
import java.util.List;

public class CustomerUser extends User {

    private int id;
    private MoneyAccount account = new MoneyAccount();
    List<CustomerScore> customerScores = new ArrayList<>();

    public List<CustomerScore> getCustomerScores() {
        return customerScores;
    }

    MoneyAccount getAccount() {
        return account;
    }

    public CustomerUser(String name, String lastName, String eMail, String phone, String address) {
        super(name, lastName, eMail, phone, address);
    }

    boolean hasPendingPunctuations() {
        return customerScores.stream().anyMatch(us -> !us.isFinished());
    }

    void addDefaultScore(Service service, Menu menu) {
        customerScores.add(new CustomerScore(this.getName(), service, menu));
    }

    CustomerScore findUserScore(String serviceName, int menuId) {
        return customerScores.stream().filter(us -> us.getService().getServiceName().equals(serviceName) &&
                us.getMenu().getMenuId() == menuId &&
                !us.isFinished())
                .findFirst().orElse(null);
    }
}
