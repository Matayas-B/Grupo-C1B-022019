package backend.service;

import backend.model.CustomerUser;
import backend.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    public CustomerUser createCustomer(CustomerUser customer) throws Exception {
        if (userExists(customer.getEmail()))
            throw new Exception();

        CustomerUser newCustomer = new CustomerUser(customer.getName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getAddress());
        return customerRepository.save(newCustomer);
    }

    public Iterable<CustomerUser> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(long id) throws Exception {
        if (!customerRepository.existsById(id))
            throw new Exception();

        customerRepository.deleteById(id);
    }

    public int depositMoney(long customerId, int money) {
        CustomerUser customer = customerRepository.findById(customerId).get();
        customer.getAccount().depositMoney(money);
        customerRepository.save(customer);
        return customer.getAccount().getFunds();
    }

    /* Private Methods */
    private boolean userExists(String emailAccount) {
        Iterable<CustomerUser> customers = customerRepository.findAll();
        return StreamSupport.stream(customers.spliterator(), false).anyMatch(c -> c.getEmail().equals(emailAccount));
    }
}
