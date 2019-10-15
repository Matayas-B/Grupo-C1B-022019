package backend.model;

import backend.model.exception.MenuNotFoundException;

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

    // Delete it ! ! ! Just for ViendasYaFacade unittesting
    CustomerScore findUserScore(String serviceName, long menuId) {
        return customerScores.stream().filter(us -> us.getServiceId().equals(serviceName) &&
                us.getMenuId().equals(menuId) &&
                !us.isFinished())
                .findFirst().orElseThrow(MenuNotFoundException::new);
    }
    
    CustomerScore findUserScore(long serviceId, long menuId) {
        return customerScores.stream().filter(us -> us.getServiceId() == serviceId &&
                us.getMenuId() == menuId &&
                !us.isFinished())
                .findFirst().orElseThrow(MenuNotFoundException::new);
    }

    CustomerScore getCustomerScoreById(int customerScoreId) {
        return customerScores.stream().filter(cs -> cs.getCustomerScoreId() == customerScoreId).findFirst().orElse(null);
    }
}
