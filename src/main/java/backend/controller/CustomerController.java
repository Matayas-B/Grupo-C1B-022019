package backend.controller;

import backend.model.CustomerUser;
import backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService = new CustomerService();

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public CustomerUser createCustomer(@Valid @RequestBody CustomerUser customer) {
        try {
            return customerService.createCustomer(customer);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public Iterable<CustomerUser> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public void deleteCustomer(long customerId) {
        try {
            customerService.deleteCustomer(customerId);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    @RequestMapping(value = "/customer/depositMoney", method = RequestMethod.GET)
    public int depositMoney(long customerId, int money) {
        try {
            return customerService.depositMoney(customerId, money);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }
}
