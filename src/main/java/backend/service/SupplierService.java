package backend.service;

import backend.controller.requests.NewServiceRequest;
import backend.model.SupplierUser;
import backend.model.ViendasYaFacade;
import backend.model.exception.InsufficientFundsException;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import backend.repository.IServiceRepository;
import backend.repository.ISupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    private ISupplierRepository supplierRepository;
    @Autowired
    private IServiceRepository serviceRepository;
    private ViendasYaFacade viendasYaFacade = new ViendasYaFacade();

    public SupplierUser createSupplier(SupplierUser supplier) {
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
        if (!supplier.hasService())
            throw new ServiceNotFoundException();

        serviceRepository.delete(supplier.getService());
        supplierRepository.save(supplier);
    }
}
