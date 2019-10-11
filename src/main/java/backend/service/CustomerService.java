package backend.service;

import backend.controller.requests.NewUserRequest;
import backend.controller.requests.PurchaseRequest;
import backend.model.CustomerUser;
import backend.model.Menu;
import backend.model.Purchase;
import backend.model.ViendasYaFacade;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import backend.repository.ICustomerRepository;
import backend.repository.IPurchaseRepository;
import backend.repository.IServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    IServiceRepository serviceRepository;
    @Autowired
    IPurchaseRepository purchaseRepository;

    ViendasYaFacade viendasYaFacade = new ViendasYaFacade();

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

    public Purchase purchaseMenu(PurchaseRequest purchaseRequest) throws Exception {
        CustomerUser customer = customerRepository.findById(purchaseRequest.getCustomerId()).orElseThrow(() -> new UserNotFoundException(purchaseRequest.getCustomerId()));
        backend.model.Service service = serviceRepository.findById(purchaseRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        Menu menu = service.getMenuByMenuId(purchaseRequest.getMenuId());

        List<Purchase> purchasedMenus = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getPurchasedMenu() == menu).collect(Collectors.toList());
        if (purchasedMenus.size() >= menu.getMaxDailySales())
            throw new Exception("Maximun number of sales per day have been reached for this menu.");

        Purchase purchase = viendasYaFacade.purchaseMenu(customer, service, menu, purchaseRequest.getQuantity());
        customerRepository.save(customer);
        serviceRepository.save(service);
        purchaseRepository.save(purchase);

        return purchase;
    }

    public Iterable<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }
}
