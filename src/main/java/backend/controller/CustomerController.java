package backend.controller;

import backend.controller.requests.NewScorePunctuationRequest;
import backend.controller.requests.NewUserRequest;
import backend.controller.requests.PurchaseRequest;
import backend.model.CustomerUser;
import backend.model.MenuScore;
import backend.model.Purchase;
import backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService = new CustomerService();

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public CustomerUser createCustomer(@Valid @RequestBody NewUserRequest customer) {
        return customerService.createCustomer(customer);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public Iterable<CustomerUser> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "/customer/getById", method = RequestMethod.GET)
    public CustomerUser getCustomerById(long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public void deleteCustomer(long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @RequestMapping(value = "/customer/depositMoney", method = RequestMethod.GET)
    public int depositMoney(long customerId, int money) {
        return customerService.depositMoney(customerId, money);
    }

    @GetMapping(value = "/customer/purchase")
    public Iterable<Purchase> getAllPurchases() {
        return customerService.getAllPurchases();
    }

    @PostMapping(value = "/customer/purchase")
    public Purchase purchaseMenu(@RequestBody PurchaseRequest purchaseRequest) throws Exception {
        return customerService.purchaseMenu(purchaseRequest);
    }

    @PostMapping(value = "/customer/score")
    public MenuScore createScoreForMenu(@Valid @RequestBody NewScorePunctuationRequest newScorePunctuationRequest) throws Exception {
        return customerService.createScoreForMenu(newScorePunctuationRequest);
    }
}
