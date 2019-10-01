package backend.service;

import backend.model.CustomerUser;
import backend.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    public CustomerUser createUser(CustomerUser customer) {
        return customerRepository.save(customer);
    }
}
