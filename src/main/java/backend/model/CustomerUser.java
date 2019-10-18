package backend.model;

import backend.model.exception.CustomerScoreNotFoundException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue(value = "customer")
public class CustomerUser extends User {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerScore> customerScores = new ArrayList<>();

    public List<CustomerScore> getCustomerScores() {
        return customerScores;
    }

    public MoneyAccount getAccount() {
        return super.getAccount();
    }

    public CustomerUser() {
        super();
    }

    public CustomerUser(String name, String lastName, String email, String password, String phone, String address) {
        super(name, lastName, email, password, phone, address);
    }

    boolean hasPendingPunctuations() {
        return customerScores.stream().anyMatch(us -> !us.isFinished());
    }

    public CustomerScore addDefaultScore(long serviceId, long menuId) {
        CustomerScore customerScore = new CustomerScore(this.getEmail(), serviceId, menuId);
        customerScores.add(customerScore);
        return customerScore;
    }

    CustomerScore findUserScore(long serviceId, long menuId) {
        return customerScores.stream().filter(us -> us.getServiceId() == serviceId &&
                us.getMenuId() == menuId &&
                !us.isFinished())
                .findFirst().orElseThrow(CustomerScoreNotFoundException::new);
    }

    CustomerScore getCustomerScoreById(long customerScoreId) {
        return customerScores.stream().filter(cs -> cs.getCustomerScoreId() == customerScoreId).findFirst().orElse(null);
    }
}
