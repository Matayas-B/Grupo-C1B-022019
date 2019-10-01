package backend.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CustomerUser extends User {

    @JoinColumn(name="MONEY_ACCOUNT_ID", unique = true)
    @OneToOne(cascade = CascadeType.ALL)
    private MoneyAccount account = new MoneyAccount();

 //   @JoinColumn(name = "CUSTOMERSCORE_ID")
   // @OneToMany(cascade = CascadeType.ALL)
    @Transient
    List<CustomerScore> customerScores = new ArrayList<>();

    public List<CustomerScore> getCustomerScores() {
        return customerScores;
    }

    MoneyAccount getAccount() {
        return account;
    }

    public CustomerUser(){
        super();
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

    CustomerScore getCustomerScoreById(int customerScoreId) {
        return customerScores.stream().filter(cs -> cs.getCustomerScoreId() == customerScoreId).findFirst().orElse(null);
    }
}
