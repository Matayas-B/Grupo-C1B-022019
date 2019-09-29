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

    CustomerScore addDefaultScore(Service service, Menu menu) {
        int nextCustomerScoreId = customerScores.size() + 1;
        CustomerScore customerScore = new CustomerScore(nextCustomerScoreId, this.getName(), service, menu);
        customerScores.add(customerScore);
        return customerScore;
    }

    CustomerScore findUserScore(String serviceName, int menuId) {
        return customerScores.stream().filter(us -> us.getService().getServiceName().equals(serviceName) &&
                us.getMenu().getMenuId() == menuId &&
                !us.isFinished())
                .findFirst().orElse(null);
    }
}
