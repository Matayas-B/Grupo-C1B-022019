package backend.controller;

import backend.controller.requests.NewScorePunctuationRequest;
import backend.controller.requests.PurchaseRequest;
import backend.model.CustomerUser;
import backend.model.HistoricalPurchases;
import backend.model.MenuScore;
import backend.model.Purchase;
import backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    @Autowired
    private CustomerService customerService = new CustomerService();

    @GetMapping
    public Iterable<CustomerUser> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/getById")
    public CustomerUser getCustomerById(long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping(value = "/depositMoney")
    public int depositMoney(long customerId, int money) {
        return customerService.depositMoney(customerId, money);
    }

    @GetMapping(value = "/purchase")
    public Iterable<HistoricalPurchases> getCustomerPurchases(long customerId) {
        return customerService.getCustomerPurchases(customerId);
    }

    @PostMapping(value = "/purchase")
    public Purchase purchaseMenu(@RequestBody PurchaseRequest purchaseRequest) throws Exception {
        return customerService.purchaseMenu(purchaseRequest);
    }

    @PostMapping(value = "/score")
    public MenuScore createScoreForMenu(@Valid @RequestBody NewScorePunctuationRequest newScorePunctuationRequest) throws Exception {
        return customerService.createScoreForMenu(newScorePunctuationRequest);
    }
}
