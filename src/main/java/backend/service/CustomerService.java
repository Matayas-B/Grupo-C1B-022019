package backend.service;

import backend.controller.requests.NewUserRequest;
import backend.model.CustomerUser;
import backend.model.exception.UserNotFoundException;
import backend.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    public CustomerUser createCustomer(NewUserRequest customer) {
        CustomerUser newCustomer = new CustomerUser(customer.getName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getPhone(), customer.getAddress());
        return customerRepository.save(newCustomer);
    }

    public Iterable<CustomerUser> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    }

    public int depositMoney(long customerId, int money) {
        CustomerUser customer = customerRepository.findById(customerId).orElseThrow(() -> new UserNotFoundException(customerId));
        customer.getAccount().depositMoney(money);
        customerRepository.save(customer);
        return customer.getAccount().getFunds();
    }
}
