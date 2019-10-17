package backend.service;

import backend.controller.requests.NewScorePunctuationRequest;
import backend.controller.requests.NewUserRequest;
import backend.controller.requests.PurchaseRequest;
import backend.model.*;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CustomerService {

    @Autowired
    private ICustomerRepository customerRepository;
    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private IPurchaseRepository purchaseRepository;
    @Autowired
    private ICustomerScoreRepository customerScoreRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    private ViendasYaFacade viendasYaFacade = new ViendasYaFacade();

    public CustomerUser createCustomer(NewUserRequest customer) {
        CustomerUser newCustomer = new CustomerUser(customer.getName(), customer.getLastName(), customer.getEmail(), customer.getPassword(), customer.getPhone(), customer.getAddress());
        return customerRepository.save(newCustomer);
    }

    public Iterable<CustomerUser> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerUser getCustomerById(long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new UserNotFoundException(customerId));
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

    public Iterable<HistoricalPurchases> getCustomerPurchases(long customerId) {
        List<Purchase> customerPurchases = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getCustomer().getId() == customerId).collect(Collectors.toList());
        return viendasYaFacade.getCustomerHistoricalPurchases(customerPurchases);
    }

    public Purchase purchaseMenu(PurchaseRequest purchaseRequest) throws Exception {
        CustomerUser customer = customerRepository.findById(purchaseRequest.getCustomerId()).orElseThrow(() -> new UserNotFoundException(purchaseRequest.getCustomerId()));
        backend.model.Service service = serviceRepository.findById(purchaseRequest.getServiceId()).orElseThrow(ServiceNotFoundException::new);
        Menu menu = service.getMenuByMenuId(purchaseRequest.getMenuId());

        List<Purchase> purchasedMenus = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getPurchasedMenu() == menu).collect(Collectors.toList());
        if (purchasedMenus.size() >= menu.getMaxDailySales())
            throw new Exception("Maximun number of sales per day have been reached for this menu.");

        CustomerScore customerScore = new CustomerScore(customer.getEmail(), service.getServiceId(), menu.getMenuId());
        Purchase purchase = viendasYaFacade.purchaseMenu(customer, service, menu, purchaseRequest.getQuantity(), customerScore);
        customerScoreRepository.save(customerScore);
        customerRepository.save(customer);
        serviceRepository.save(service);
        purchaseRepository.save(purchase);

        return purchase;
    }

    public MenuScore createScoreForMenu(NewScorePunctuationRequest newScorePunctuationRequest) throws Exception {
        CustomerUser customer = getCustomerById(newScorePunctuationRequest.getCustomerId());
        backend.model.Service service = getServiceById(newScorePunctuationRequest.getServiceId());
        Menu menu = service.getMenuByMenuId(newScorePunctuationRequest.getMenuId());

        MenuScore menuScore = viendasYaFacade.createMenuScore(customer, service, menu, newScorePunctuationRequest.getPunctuation());
        viendasYaFacade.checkMenuAndServiceValidity(service, menu);

        if (!menu.isValidMenu())
            sendEmail(service.getSupplier().getEmail(), "You have lost a menu ! ! !", String.format("Menu with name %s has been marked as invalid. We are sorry for that :(.", menu.getName()));
        if (!service.isValidService())
            sendEmail(service.getSupplier().getEmail(), "Man, your service sucks !", String.format("The %s service you used to provide, has been marked as invalid, based on customers complaints.", service.getServiceName()));

        serviceRepository.save(service);
        customerRepository.save(customer);

        return menuScore;
    }

    /* Private Methods */
    private backend.model.Service getServiceById(long serviceId) throws ServiceNotFoundException {
        return serviceRepository.findById(serviceId).orElseThrow(ServiceNotFoundException::new);
    }

    private void sendEmail(String toMail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toMail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
}
