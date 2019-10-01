package backend.controller;

import backend.model.CustomerUser;
import backend.repository.ICustomerRepository;
import backend.service.CustomerService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private CustomerService customerService = new CustomerService();

    @RequestMapping (value = "/customer", method= RequestMethod.POST)
    public CustomerUser createUser(@RequestBody CustomerUser customer){
        return customerService.createUser(customer);
    }

}
