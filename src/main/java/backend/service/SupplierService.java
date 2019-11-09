package backend.service;

import backend.controller.requests.NewServiceRequest;
import backend.controller.requests.NewUserRequest;
import backend.model.HistoricalPurchases;
import backend.model.Purchase;
import backend.model.SupplierUser;
import backend.model.ViendasYaFacade;
import backend.model.exception.InsufficientFundsException;
import backend.model.exception.PurchaseNotFoundException;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import backend.repository.IPurchaseRepository;
import backend.repository.IServiceRepository;
import backend.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;
    @Autowired
    private IServiceRepository serviceRepository;
    @Autowired
    private IPurchaseRepository purchaseRepository;

    private ViendasYaFacade viendasYaFacade = new ViendasYaFacade();

    public SupplierUser createSupplier(NewUserRequest supplier) {
        SupplierUser newSupplier = new SupplierUser(supplier.getName(), supplier.getLastName(), supplier.getEmail(), supplier.getPassword(), supplier.getPhone(), supplier.getAddress());
        return supplierRepository.save(newSupplier);
    }

    public Iterable<SupplierUser> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public void deleteSupplier(long id) {
        supplierRepository.deleteById(id);
    }

    public int extractMoney(long supplierId, int money) throws InsufficientFundsException, UserNotFoundException {
        SupplierUser supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new UserNotFoundException(supplierId));
        supplier.getAccount().extractMoney(money);
        supplierRepository.save(supplier);
        return supplier.getAccount().getFunds();
    }

    public void addService(NewServiceRequest newServiceRequest) throws Exception {
        SupplierUser supplier = supplierRepository.findById(newServiceRequest.getSupplierId()).orElseThrow(() -> new UserNotFoundException(newServiceRequest.getSupplierId()));
        viendasYaFacade.addServiceToSupplier(supplier, newServiceRequest.getServiceName(), newServiceRequest.getIcon(), newServiceRequest.getAddressTown(), newServiceRequest.getAddressLocation(), newServiceRequest.getDescription(), newServiceRequest.getEmail(), newServiceRequest.getPhoneNumber(), newServiceRequest.getOfficeDays(), newServiceRequest.getOfficeHours(), newServiceRequest.getDeliveryDistance());
        supplierRepository.save(supplier);
    }

    public backend.model.Service getSupplierService(long supplierId) throws ServiceNotFoundException, UserNotFoundException {
        SupplierUser supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new UserNotFoundException(supplierId));
        if (!supplier.hasService())
            throw new ServiceNotFoundException();

        return supplier.getService();
    }

    public void deleteService(long supplierId) throws ServiceNotFoundException, UserNotFoundException {
        SupplierUser supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new UserNotFoundException(supplierId));
        backend.model.Service service = serviceRepository.findById(supplier.getService().getServiceId()).orElseThrow(ServiceNotFoundException::new);
        service.setSupplier(null);
        supplier.setService(null);
        serviceRepository.delete(service);
        supplierRepository.save(supplier);
    }

    public Iterable<HistoricalPurchases> getSupplierPurchases(long supplierId) {
        List<Purchase> supplierPurchases = StreamSupport.stream(purchaseRepository.findAll().spliterator(), false).filter(p -> p.getService().getSupplier().getId() == supplierId).collect(Collectors.toList());
        return viendasYaFacade.getSupplierHistoricalPurchases(supplierPurchases);
    }

    public void startPurchaseDelivery(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(PurchaseNotFoundException::new);
        viendasYaFacade.startDeliveryForPurchase(purchase);
        purchaseRepository.save(purchase);
    }

    public void finishPurchaseDelivery(long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).orElseThrow(PurchaseNotFoundException::new);
        viendasYaFacade.finishDeliveryForPurchase(purchase);
        purchaseRepository.save(purchase);
    }

    public SupplierUser getSupplierById(long supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(() -> new UserNotFoundException(supplierId));
    }
}
