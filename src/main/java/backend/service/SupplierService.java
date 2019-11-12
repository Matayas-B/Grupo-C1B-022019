package backend.service;

import backend.controller.requests.ServiceRequest;
import backend.controller.requests.NewUserRequest;
import backend.model.*;
import backend.model.exception.InsufficientFundsException;
import backend.model.exception.PurchaseNotFoundException;
import backend.model.exception.ServiceNotFoundException;
import backend.model.exception.UserNotFoundException;
import backend.repository.IPurchaseRepository;
import backend.repository.IServiceRepository;
import backend.repository.ISupplierRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
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

    public void addService(ServiceRequest serviceRequest) throws Exception {
        SupplierUser supplier = supplierRepository.findById(serviceRequest.getSupplierId()).orElseThrow(() -> new UserNotFoundException(serviceRequest.getSupplierId()));
        viendasYaFacade.addServiceToSupplier(supplier, serviceRequest.getServiceName(), serviceRequest.getIcon(), serviceRequest.getAddressTown(), serviceRequest.getAddressLocation(), serviceRequest.getDescription(), serviceRequest.getEmail(), serviceRequest.getPhoneNumber(), serviceRequest.getOfficeDays(), serviceRequest.getOfficeHours(), serviceRequest.getDeliveryDistance());
        supplierRepository.save(supplier);
    }

    public void updateService(ServiceRequest serviceRequest) {
        SupplierUser supplier = supplierRepository.findById(serviceRequest.getSupplierId()).orElseThrow(() -> new UserNotFoundException(serviceRequest.getSupplierId()));
        if (!supplier.hasService() && supplier.getService().getServiceId() != serviceRequest.getServiceId())
            throw new ServiceNotFoundException();

        backend.model.Service serviceToUpdate = mapDTOToService(serviceRequest);
        serviceRepository.save(serviceToUpdate);
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

    private backend.model.Service mapDTOToService(ServiceRequest serviceRequest) {
        ModelMapper mapper = new ModelMapper();
        Converter<ServiceRequest, backend.model.Service> addressConverter = new Converter<ServiceRequest, backend.model.Service>() {
            @Override
            public backend.model.Service convert(MappingContext<ServiceRequest, backend.model.Service> mappingContext) {
                backend.model.Service mappedService = new backend.model.Service();

                mappedService.setAddress(new Address(mappingContext.getSource().getAddressTown(), mappingContext.getSource().getAddressLocation()));
                return mappedService;
            }
        };
        mapper.addConverter(addressConverter);

        return mapper.map(serviceRequest, backend.model.Service.class);
    }
}
